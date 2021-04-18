package core.concurrent.promise;

import core.Function;

public interface Promise<T> {
    Result<T> getResult();

    <R> Promise<R> onFulfilled(Function<? super T, R, Exception> handler);

    <R> Promise<R> onRejected(Function<Exception, R, Exception> handler);

    <R> Promise<R> then(Function<? super T, R, Exception> fulfilledHandler, Function<Exception, R, Exception> failureHandler);

    <R> Promise<R> then(Function<Result<T>, Promise<R>, Exception> callback);

    <R> Promise<R> map(Function<Result<T>, R, Exception> callback);

    void onFinally(Runnable task);
}
