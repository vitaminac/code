package ioc.config;

public class TestThreadLocalImpl implements TestThreadLocal {
    private final long id;

    public TestThreadLocalImpl() {
        this.id = Thread.currentThread().getId();
    }

    @Override
    public long getState() {
        return id;
    }
}
