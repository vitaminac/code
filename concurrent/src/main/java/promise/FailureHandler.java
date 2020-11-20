package promise;

public interface FailureHandler<R> {
    R doCatch(Exception e) throws Exception;
}