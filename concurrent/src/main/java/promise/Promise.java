package promise;

import scheduler.Delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Promise<Thing> {
    static <T> Promise<List<T>> all(List<Promise<T>> promises) {
        return DeferredPromise.from(promise -> {
            List<T> results = new ArrayList<>(promises.size());
            Promise<Void> chain = DeferredPromise.from(p -> p.resolve(null));
            for (Promise<T> next : promises) {
                chain = chain.then(none -> DeferredPromise.from(p -> {
                    next.then(r -> {
                        results.add(r);
                        p.resolve(null);
                        return null;
                    }, e -> {
                        p.reject(e);
                        return null;
                    });
                }));
            }
            chain.then(none -> {
                promise.resolve(results);
                return null;
            }, e -> {
                promise.reject(e);
                return null;
            });
        });
    }

    boolean isDone();

    boolean isFailed();

    boolean isPending();

    Optional<Thing> getResult() throws Exception;

    void resolve(Thing thing);

    void reject(Exception reason);

    <R> Promise<R> onFulfilled(FulfilledHandler<? super Thing, R> handler);

    <R> Promise<R> onRejected(FailureHandler<R> handler);

    <R> Promise<R> then(FulfilledHandler<? super Thing, R> fulfilledHandler, FailureHandler<R> failureHandler);

    <R> Promise<R> then(Delegate<Thing, Promise<R>, Exception> delegate);

    void onFinally(DoneCallback callback);
}
