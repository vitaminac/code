package promise;

import scheduler.Delegate;
import scheduler.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeferredPromise<D> implements Promise<D> {
    public static <D> DeferredPromise<D> from(Executor<Promise<D>> promiser) {
        final DeferredPromise<D> promise = new DeferredPromise<>();
        try {
            promiser.execute(promise);
        } catch (Exception e) {
            promise.reject(e);
        }
        return promise;
    }

    private State state;
    private Object result;
    private List<PipedPromise<? super D, ?>> chains = new ArrayList<>();

    public DeferredPromise() {
        this.state = State.Pending;
    }

    @Override
    public void onFinally(DoneCallback callback) {
        this.then(result -> {
            callback.doCallback();
            return null;
        }, (FailureHandler<Void>) e -> {
            callback.doCallback();
            return null;
        });
    }

    @Override
    public <R> Promise<R> onFulfilled(FulfilledHandler<? super D, R> handler) {
        return this.then(handler, null);
    }

    @Override
    public <R> Promise<R> onRejected(FailureHandler<R> handler) {
        return this.then(null, handler);
    }

    @Override
    public void reject(Exception reason) {
        this.state = State.Rejected;
        this.result = reason;
        for (PipedPromise<? super D, ?> next : this.chains) {
            next.pipe(reason);
        }
    }

    @Override
    public boolean isDone() {
        return this.state == State.Fulfilled;
    }

    @Override
    public boolean isFailed() {
        return this.state == State.Rejected;
    }

    @Override
    public boolean isPending() {
        return this.state == State.Pending;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<D> getResult() throws Exception {
        if (this.result == null) {
            return Optional.empty();
        }
        switch (this.state) {
            case Fulfilled:
                return Optional.of((D) this.result);
            case Rejected:
                throw (Exception) this.result;
            default:
                return Optional.empty();
        }
    }

    @Override
    public void resolve(D result) {
        this.state = State.Fulfilled;
        this.result = result;
        for (PipedPromise<? super D, ?> next : this.chains) {
            next.pipe(result);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Promise<R> then(FulfilledHandler<? super D, R> fulfilledHandler, FailureHandler<R> failureHandler) {
        final DeferredPromise<R> ret = new DeferredPromise<>();
        switch (this.state) {
            case Fulfilled:
                if (fulfilledHandler != null) {
                    try {
                        ret.resolve(fulfilledHandler.doNext((D) this.result));
                    } catch (Exception e) {
                        ret.reject(e);
                    }
                } else {
                    ret.resolve(null);
                }
                return ret;
            case Rejected:
                if (failureHandler != null) {
                    try {
                        ret.resolve(failureHandler.doCatch((Exception) this.result));
                    } catch (Exception e) {
                        ret.reject(e);
                    }
                } else {
                    ret.resolve(null);
                }
                return ret;
            default:
                final PipedValuePromise<? super D, R> promise = new PipedValuePromise<>(fulfilledHandler, failureHandler);
                this.chains.add(promise);
                return promise;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Promise<R> then(Delegate<D, Promise<R>, Exception> delegate) {
        switch (this.state) {
            case Fulfilled:
                try {
                    return delegate.delegate((D) this.result);
                } catch (Exception e) {
                    final DeferredPromise<R> ret = new DeferredPromise<>();
                    ret.reject(e);
                    return ret;
                }
            case Rejected:
                final DeferredPromise<R> ret = new DeferredPromise<>();
                ret.reject((Exception) this.result);
                return ret;
            default:
                final DelegatingPromise<D, R> promise = DelegatingPromise.from(delegate);
                this.chains.add(promise);
                return promise;
        }
    }
}
