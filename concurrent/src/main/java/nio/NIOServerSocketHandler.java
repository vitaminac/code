package nio;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public interface NIOServerSocketHandler {
    void onListen(ServerSocketChannel ssc);

    void onAccept(SocketChannel sc);

    void onClose();
}
