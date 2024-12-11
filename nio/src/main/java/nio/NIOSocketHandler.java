package nio;

import java.nio.ByteBuffer;

public interface NIOSocketHandler {
    void onConnected(NIOSocket socket);

    void onData(ByteBuffer data);

    void onEnd();

    void onDrain();

    void onClose();

    // TODO: pass error from nio.EventLoop to here
    void onError(Exception e);
}
