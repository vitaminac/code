package core.concurrent;

import core.Function;
import core.dp.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Promise<T> {
    public interface PromiseCallback<T> {
        void callback(final Consumer<T> resolve, final Consumer<Exception> reject);
    }

    private enum State {
        // initial state, neither fulfilled nor rejected.
        PENDING() {
            @Override
            public <T> void fulfilled(T result, Promise<T> promise) {
                promise.result.state = State.FULFILLED;
                promise.result.value = result;
                promise.subject.publish(promise.result);
            }

            @Override
            public <T> void reject(Exception reason, Promise<T> promise) {
                promise.result.state = State.REJECTED;
                promise.result.value = reason;
                promise.subject.publish(promise.result);
            }

            @Override
            public <T, R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback, Promise<T> promise) {
                Promise<R> next = new Promise<>();
                promise.subject.subscribe(result -> {
                    if (result.state == State.FULFILLED) {
                        try {
                            next.fulfilled(callback.callback(core.concurrent.promise.Result.success(result.get())));
                        } catch (Exception e) {
                            next.reject(e);
                        }
                    } else {
                        next.reject(result.getReason());
                    }
                });
                return next;
            }

            @Override
            public <T, R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback, Promise<T> promise) {
                final Promise<R> next = new Promise<>();
                promise.subject.subscribe(new Consumer<>() {
                    @Override
                    public void accept(final Result<T> result) {
                        if (result.state == State.FULFILLED) {
                            try {
                                final T value = result.get();
                                final Promise<R> nextPromise = callback.callback(core.concurrent.promise.Result.success(value));
                                nextPromise.then((Function<R, Void, Exception>) nextResult -> {
                                    next.fulfilled(nextResult);
                                    return null;
                                }, e -> {
                                    next.reject(e);
                                    return null;
                                });
                            } catch (Exception e) {
                                next.reject(e);
                            }
                        } else {
                            next.reject(result.getReason());
                        }
                    }
                });
                return next;
            }
        },
        // meaning that the operation completed successfully.
        FULFILLED() {
            @Override
            public <T> void fulfilled(T result, Promise<T> promise) {
                throw new IllegalStateException("already fulfilled");
            }

            @Override
            public <T> void reject(Exception reason, Promise<T> promise) {
                throw new IllegalStateException("already fulfilled");
            }

            @Override
            public <T, R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback, Promise<T> promise) {
                Promise<R> next = new Promise<>();
                try {
                    next.fulfilled(callback.callback(promise.getResult()));
                } catch (Exception reason) {
                    next.reject(reason);
                }
                return next;
            }

            @Override
            public <T, R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback, Promise<T> promise) {
                try {
                    return callback.callback(core.concurrent.promise.Result.success(promise.result.get()));
                } catch (Exception e) {
                    final Promise<R> ret = new Promise<>();
                    ret.reject(e);
                    return ret;
                }
            }
        },
        // meaning that the operation failed.
        REJECTED() {
            @Override
            public <T> void fulfilled(T result, Promise<T> promise) {
                throw new IllegalStateException("already rejected");
            }

            @Override
            public <T> void reject(Exception reason, Promise<T> promise) {
                throw new IllegalStateException("already rejected");
            }

            @Override
            public <T, R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback, Promise<T> promise) {
                final Promise<R> ret = new Promise<>();
                ret.reject(promise.result.getReason());
                return ret;
            }

            @Override
            public <T, R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback, Promise<T> promise) {
                final Promise<R> ret = new Promise<>();
                ret.reject(promise.result.getReason());
                return ret;
            }
        };

        public abstract <T> void fulfilled(T result, Promise<T> promise);

        public abstract <T> void reject(Exception reason, Promise<T> promise);

        public abstract <T, R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback, Promise<T> promise);

        public abstract <T, R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback, Promise<T> promise);
    }

    private void fulfilled(T result) {
        this.result.state.fulfilled(result, this);
    }

    private void reject(Exception reason) {
        this.result.state.reject(reason, this);
    }

    private static class Result<T> implements core.concurrent.promise.Result<T> {
        private Object value;
        private State state = State.PENDING;

        @Override
        @SuppressWarnings("unchecked")
        public T get() {
            return (T) value;
        }

        @Override
        public Exception getReason() {
            return (Exception) value;
        }

        @Override
        public boolean isFulfilled() {
            return this.state == State.FULFILLED;
        }

        @Override
        public boolean isFailed() {
            return this.state == State.REJECTED;
        }

        @Override
        public boolean isPending() {
            return this.state == State.PENDING;
        }
    }

    private Subject<Result<T>> subject = new Subject<>();

    private Result<T> result = new Result<>();

    private Promise() {
    }

    public Promise(final PromiseCallback<T> callback) {
        callback.callback(this::fulfilled, this::reject);
    }

    public <R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback) {
        return this.result.state.map(callback, this);
    }

    public <R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback) {
        return this.result.state.then(callback, this);
    }

    public <R> Promise<R> then(Function<? super T, R, Exception> onFulfilledHandler, Function<Exception, R, Exception> onRejectedHandler) {
        final Promise<R> ret = new Promise<>();
        switch (this.result.state) {
            case FULFILLED:
                if (onFulfilledHandler != null) {
                    try {
                        final T value = this.result.get();
                        ret.fulfilled(onFulfilledHandler.callback(value));
                    } catch (Exception e) {
                        ret.reject(e);
                    }
                } else {
                    ret.fulfilled(null);
                }
                return ret;
            case REJECTED:
                if (onRejectedHandler != null) {
                    try {
                        ret.fulfilled(onRejectedHandler.callback(this.result.getReason()));
                    } catch (Exception e) {
                        ret.reject(e);
                    }
                } else {
                    ret.fulfilled(null);
                }
                return ret;
            default:
                final Promise<R> promise = new Promise<>();
                this.subject.subscribe(new Consumer<>() {
                    @Override
                    public void accept(final Result<T> result) {
                        if (result.state == State.FULFILLED) {
                            if (onFulfilledHandler != null) {
                                try {
                                    final T value = result.get();
                                    promise.fulfilled(onFulfilledHandler.callback(value));
                                } catch (Exception e) {
                                    promise.reject(e);
                                }
                            } else {
                                promise.fulfilled(null);
                            }
                        } else {
                            if (onRejectedHandler != null) {
                                try {
                                    promise.fulfilled(onRejectedHandler.callback(result.getReason()));
                                } catch (Exception e) {
                                    promise.reject(e);
                                }
                            } else {
                                promise.reject(result.getReason());
                            }
                        }
                    }
                });
                return promise;
        }
    }

    public core.concurrent.promise.Result<T> getResult() {
        return this.result;
    }

    public void onFinally(Runnable task) {
        this.then(result -> {
            task.run();
            return null;
        }, (Function<Exception, Void, Exception>) e -> {
            task.run();
            return null;
        });
    }

    public <R> Promise<R> onFulfilled(Function<? super T, R, Exception> handler) {
        return this.then(handler, null);
    }

    public static <T> Promise<List<T>> all(final List<Promise<T>> promises) {
        Promise<List<T>> chain = new Promise<>((resolve, reject) -> resolve.accept(new ArrayList<>()));
        for (final var next : promises) {
            chain = chain.then(rList -> next.then(result -> {
                final List<T> list = rList.get();
                if (result.isFailed()) throw result.getReason();
                list.add((T) result.get());
                return new Promise<>((resolve, reject) -> resolve.accept(list));
            }));
        }
        return chain;
    }
}
