package provider;

import injection.Injectable;
import injection.Scope;

@Injectable(scope = Scope.Thread)
public class TestThreadLocal {
    private final long id;

    public TestThreadLocal() {
        this.id = Thread.currentThread().getId();
    }

    public long getId() {
        return id;
    }
}
