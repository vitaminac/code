package promise;

public interface FulfilledHandler<P, R> {
    R doNext(P result) throws Exception;
}