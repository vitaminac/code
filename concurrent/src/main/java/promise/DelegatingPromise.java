package promise;

import scheduler.Delegate;

public class DelegatingPromise<P, R> extends DeferredPromise<R> implements Promise<R>, PipedPromise<P, R> {
    private final Delegate<P, Promise<R>, Exception> delegate;

    private DelegatingPromise(Delegate<P, Promise<R>, Exception> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void pipe(P value) {
        try {
            final Promise<R> promise = this.delegate.delegate(value);
            promise.then((FulfilledHandler<R, Void>) result -> {
                this.resolve(result);
                return null;
            }, e -> {
                this.reject(e);
                return null;
            });
        } catch (Exception e) {
            this.reject(e);
        }
    }

    @Override
    public void pipe(Exception reason) {
        this.reject(reason);
    }

    public static <P, R> DelegatingPromise<P, R> from(Delegate<P, Promise<R>, Exception> delegate) {
        return new DelegatingPromise<>(delegate);
    }
}
