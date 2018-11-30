package provider;

import injection.Injectable;
import injection.Scope;

@Injectable(scope = Scope.Thread)
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
