package core.concurrent;

import core.Function;
import core.concurrent.promise.Promise;
import core.dp.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DeferredPromise<T> implements Promise<T> {
    private enum State {
        // initial state, neither fulfilled nor rejected.
        PENDING() {
            @Override
            public <T> void fulfilled(T result, DeferredPromise<T> promise) {
                promise.result.state = State.FULFILLED;
                promise.result.value = result;
                promise.subject.publish(promise.result);
            }

            @Override
            public <T> void reject(Exception reason, DeferredPromise<T> promise) {
                promise.result.state = State.REJECTED;
                promise.result.value = reason;
                promise.subject.publish(promise.result);
            }

            @Override
            public <T, R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback, DeferredPromise<T> promise) {
                DeferredPromise<R> next = new DeferredPromise<>();
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
            public <T, R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback, DeferredPromise<T> promise) {
                final DeferredPromise<R> next = new DeferredPromise<>();
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
            public <T> void fulfilled(T result, DeferredPromise<T> promise) {
                throw new IllegalStateException("already fulfilled");
            }

            @Override
            public <T> void reject(Exception reason, DeferredPromise<T> promise) {
                throw new IllegalStateException("already fulfilled");
            }

            @Override
            public <T, R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback, DeferredPromise<T> promise) {
                DeferredPromise<R> next = new DeferredPromise<>();
                try {
                    next.fulfilled(callback.callback(promise.getResult()));
                } catch (Exception reason) {
                    next.reject(reason);
                }
                return next;
            }

            @Override
            public <T, R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback, DeferredPromise<T> promise) {
                try {
                    return callback.callback(core.concurrent.promise.Result.success(promise.result.get()));
                } catch (Exception e) {
                    final DeferredPromise<R> ret = new DeferredPromise<>();
                    ret.reject(e);
                    return ret;
                }
            }
        },
        // meaning that the operation failed.
        REJECTED() {
            @Override
            public <T> void fulfilled(T result, DeferredPromise<T> promise) {
                throw new IllegalStateException("already rejected");
            }

            @Override
            public <T> void reject(Exception reason, DeferredPromise<T> promise) {
                throw new IllegalStateException("already rejected");
            }

            @Override
            public <T, R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback, DeferredPromise<T> promise) {
                final DeferredPromise<R> ret = new DeferredPromise<>();
                ret.reject(promise.result.getReason());
                return ret;
            }

            @Override
            public <T, R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback, DeferredPromise<T> promise) {
                final DeferredPromise<R> ret = new DeferredPromise<>();
                ret.reject(promise.result.getReason());
                return ret;
            }
        };

        public abstract <T> void fulfilled(T result, DeferredPromise<T> promise);

        public abstract <T> void reject(Exception reason, DeferredPromise<T> promise);

        public abstract <T, R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback, DeferredPromise<T> promise);

        public abstract <T, R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback, DeferredPromise<T> promise);
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

    private DeferredPromise() {
    }

    @Override
    public <R> Promise<R> map(Function<core.concurrent.promise.Result<T>, R, Exception> callback) {
        return this.result.state.map(callback, this);
    }

    @Override
    public <R> Promise<R> then(Function<core.concurrent.promise.Result<T>, Promise<R>, Exception> callback) {
        return this.result.state.then(callback, this);
    }

    @Override
    public <R> Promise<R> then(Function<? super T, R, Exception> fulfilledHandler, Function<Exception, R, Exception> failureHandler) {
        final DeferredPromise<R> ret = new DeferredPromise<>();
        switch (this.result.state) {
            case FULFILLED:
                if (fulfilledHandler != null) {
                    try {
                        final T value = this.result.get();
                        ret.fulfilled(fulfilledHandler.callback(value));
                    } catch (Exception e) {
                        ret.reject(e);
                    }
                } else {
                    ret.fulfilled(null);
                }
                return ret;
            case REJECTED:
                if (failureHandler != null) {
                    try {
                        ret.fulfilled(failureHandler.callback(this.result.getReason()));
                    } catch (Exception e) {
                        ret.reject(e);
                    }
                } else {
                    ret.fulfilled(null);
                }
                return ret;
            default:
                final DeferredPromise<R> promise = new DeferredPromise<>();
                this.subject.subscribe(new Consumer<>() {
                    @Override
                    public void accept(final Result<T> result) {
                        if (result.state == State.FULFILLED) {
                            if (fulfilledHandler != null) {
                                try {
                                    final T value = result.get();
                                    promise.fulfilled(fulfilledHandler.callback(value));
                                } catch (Exception e) {
                                    promise.reject(e);
                                }
                            } else {
                                promise.fulfilled(null);
                            }
                        } else {
                            if (failureHandler != null) {
                                try {
                                    promise.fulfilled(failureHandler.callback(result.getReason()));
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

    @Override
    public core.concurrent.promise.Result<T> getResult() {
        return this.result;
    }

    @Override
    public void onFinally(Runnable task) {
        this.then(result -> {
            task.run();
            return null;
        }, (Function<Exception, Void, Exception>) e -> {
            task.run();
            return null;
        });
    }

    @Override
    public <R> Promise<R> onFulfilled(Function<? super T, R, Exception> handler) {
        return this.then(handler, null);
    }

    @Override
    public <R> Promise<R> onRejected(Function<Exception, R, Exception> handler) {
        return this.then(null, handler);
    }

    public interface DeferredActor<T> {
        void fulfill(T value);

        void reject(Exception reason);
    }

    public static <T> DeferredPromise<T> from(final Consumer<DeferredActor<T>> deferredAction) {
        final DeferredPromise<T> promise = new DeferredPromise<>();
        try {
            deferredAction.accept(new DeferredActor<T>() {
                @Override
                public void fulfill(T value) {
                    promise.fulfilled(value);
                }

                @Override
                public void reject(Exception reason) {
                    promise.reject(reason);
                }
            });
        } catch (Exception e) {
            promise.reject(e);
        }
        return promise;
    }

    public static <T> Promise<List<T>> all(final List<Promise<T>> promises) {
        Promise<List<T>> chain = DeferredPromise.from((actor) -> actor.fulfill(new ArrayList<>()));
        for (final var next : promises) {
            chain = chain.then(rList -> next.then(result -> {
                final List<T> list = rList.get();
                if (result.isFailed()) throw result.getReason();
                list.add((T) result.get());
                return DeferredPromise.from((actor) -> actor.fulfill(list));
            }));
        }
        return chain;
    }
}
