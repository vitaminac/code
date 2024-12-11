package nio;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface NIOSocket {
    void write(ByteBuffer buffer);

    void close() throws IOException;

    void shutdownInput() throws IOException;

    void shutdownOutput() throws IOException;
}
