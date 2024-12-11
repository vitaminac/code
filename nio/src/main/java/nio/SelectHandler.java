package nio;

import java.io.Closeable;
import java.io.IOException;

// TODO: rename to EventHandle
public interface SelectHandler extends Closeable {
    void select() throws IOException;
}
