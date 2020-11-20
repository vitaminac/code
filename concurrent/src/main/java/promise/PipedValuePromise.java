package promise;

class PipedValuePromise<P, R> extends DeferredPromise<R> implements PipedPromise<P, R> {
    private final FulfilledHandler<P, R> fulfilledHandler;
    private final FailureHandler<R> failureHandler;

    PipedValuePromise(FulfilledHandler<P, R> fulfilledHandler, FailureHandler<R> failureHandler) {
        this.fulfilledHandler = fulfilledHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public void pipe(P value) {
        if (this.fulfilledHandler != null) {
            try {
                this.resolve(fulfilledHandler.doNext(value));
            } catch (Exception e) {
                this.reject(e);
            }
        } else {
            this.resolve(null);
        }
    }

    @Override
    public void pipe(Exception reason) {
        if (this.failureHandler != null) {
            try {
                this.resolve(this.failureHandler.doCatch(reason));
            } catch (Exception e) {
                this.reject(e);
            }
        } else {
            this.reject(reason);
        }
    }
}
