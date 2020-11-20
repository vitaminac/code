package nio;

import org.junit.Before;
import org.junit.Test;
import promise.DeferredPromise;
import promise.Promise;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EventLoopTest {
    private EventLoop EVENT_LOOP;

    @Before
    public void setUp() {
        EVENT_LOOP = EventLoop.create();
    }

    @Test
    public void deferTest() {
        assertNotNull(EVENT_LOOP);
        final List<Integer> results = new ArrayList<>();
        EVENT_LOOP.defer(() -> results.add(135), 135);
        EVENT_LOOP.defer(() -> results.add(861), 861);
        EVENT_LOOP.defer(() -> results.add(514), 514);
        EVENT_LOOP.defer(() -> results.add(346), 346);
        EVENT_LOOP.defer(() -> results.add(498), 498);
        EVENT_LOOP.defer(() -> results.add(628), 628);
        EVENT_LOOP.defer(() -> results.add(184), 184);
        EVENT_LOOP.defer(() -> results.add(754), 754);
        EVENT_LOOP.defer(EVENT_LOOP::stop, 1500);
        EVENT_LOOP.run();
        assertEquals(Arrays.asList(135, 184, 346, 498, 514, 628, 754, 861), results);
    }

    @Test
    public void arrangeTest() {
        assertNotNull(EVENT_LOOP);
        final boolean[] refCalled = new boolean[1];
        EVENT_LOOP.arrange(() -> refCalled[0] = true);
        EVENT_LOOP.arrange(EVENT_LOOP::stop);
        EVENT_LOOP.run();
        assertTrue(refCalled[0]);
    }

    @Test
    public void promiseTest() {
        DeferredPromise<Integer> promise = DeferredPromise.from(p -> EVENT_LOOP.arrange(() -> p.resolve(1)));
        final int[] refResult = new int[]{0};
        final boolean[] refNotCalled = new boolean[1];
        promise.then(n -> {
            refResult[0] = n;
            return null;
        }, e -> refNotCalled[0] = true);
        EVENT_LOOP.arrange(EVENT_LOOP::stop);
        EVENT_LOOP.run();
        assertEquals(refResult[0], 1);
        assertFalse(refNotCalled[0]);
    }

    @Test
    public void promiseAllTest() {
        assertNotNull(EVENT_LOOP);
        final Promise<Integer> p1 = DeferredPromise.from(p -> EVENT_LOOP.defer(() -> p.resolve(101), 1000));
        final Promise<Integer> p2 = DeferredPromise.from(p -> EVENT_LOOP.defer(() -> p.resolve(102), 800));
        final Promise<Integer> p3 = DeferredPromise.from(p -> EVENT_LOOP.defer(() -> p.resolve(103), 100));
        final Promise<List<Integer>> all = Promise.all(Arrays.asList(p1, p2, p3));
        final List<Integer> results = new ArrayList<>();
        all.onFulfilled(integers -> {
            results.addAll(integers);
            return null;
        });
        EVENT_LOOP.defer(EVENT_LOOP::stop, 1500);
        EVENT_LOOP.run();
        assertEquals(Arrays.asList(101, 102, 103), results);
        assertEquals(results.size(), 3);
    }

    @Test
    public void allPromisesAndThrow() {
        assertNotNull(EVENT_LOOP);
        final Promise<Integer> p1 = DeferredPromise.from(p -> EVENT_LOOP.defer(() -> p.resolve(104), 1100));
        final Promise<Integer> p2 = DeferredPromise.from(p -> EVENT_LOOP.defer(() -> p.reject(new RuntimeException("105")), 900));
        final Promise<Integer> p3 = DeferredPromise.from(p -> EVENT_LOOP.defer(() -> p.resolve(106), 200));
        final Promise<List<Integer>> all = Promise.all(Arrays.asList(p1, p2, p3));
        final List<Object> results = new ArrayList<>();
        final boolean[] refCalled = new boolean[1];
        all.then(integers -> {
            results.addAll(integers);
            return null;
        }, e -> {
            refCalled[0] = true;
            return null;
        });
        EVENT_LOOP.defer(EVENT_LOOP::stop, 1500);
        EVENT_LOOP.run();
        assertEquals(0, results.size());
        assertTrue(refCalled[0]);
    }

    @Test
    public void socketTest() throws IOException {
        final int PORT = 13458;
        final List<Object> results = new ArrayList<>();
        EVENT_LOOP.listen(PORT, new NIOServerSocketHandler() {
            @Override
            public void onListen(ServerSocketChannel ssc) {
                results.add("STARTED SERVER SOCKET");
            }

            @Override
            public void onAccept(SocketChannel sc) {
                results.add("ACCEPTED CONNECTION FROM CLIENT");
                try {
                    EVENT_LOOP.accept(sc, new NIOSocketHandler() {
                        private StringBuilder sb = new StringBuilder();
                        private NIOSocket socket;

                        @Override
                        public void onConnected(NIOSocket socket) {
                            results.add("CONNECTED TO CLIENT");
                            this.socket = socket;
                        }

                        @Override
                        public void onData(ByteBuffer data) {
                            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(data);
                            String text = charBuffer.toString();
                            this.sb.append(text);
                        }

                        @Override
                        public void onEnd() {
                            results.add(this.sb.toString());
                            try {
                                this.socket.write(ByteBuffer.wrap("RECEIVED ACK FROM SERVER".getBytes()));
                                this.socket.shutdownInput();
                            } catch (IOException e) {
                                results.add(e.getMessage());
                            }
                        }

                        @Override
                        public void onDrain() {
                            try {
                                results.add("SENT ACK FROM SERVER");
                                this.socket.shutdownOutput();
                            } catch (IOException e) {
                                results.add(e.getMessage());
                            }
                        }

                        @Override
                        public void onClose() {
                            results.add("CLOSING CONNECTION FROM SERVER SIDE");
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
                } catch (IOException e) {
                    results.add(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose() {
                results.add("CLOSING SERVER SOCKET");
            }
        });
        EVENT_LOOP.arrange(() -> EVENT_LOOP.connect(new InetSocketAddress(PORT), new NIOSocketHandler() {
            private StringBuilder sb = new StringBuilder();
            private NIOSocket socket;

            @Override
            public void onConnected(NIOSocket socket) {
                results.add("CONNECTED TO SERVER");
                this.socket = socket;
                this.socket.write(ByteBuffer.wrap("RECEIVED SYN FROM CLIENT".getBytes()));
            }

            @Override
            public void onData(ByteBuffer data) {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(data);
                String text = charBuffer.toString();
                this.sb.append(text);
            }

            @Override
            public void onEnd() {
                results.add(this.sb.toString());
                try {
                    socket.shutdownInput();
                    EVENT_LOOP.arrange(() -> socket.close());
                } catch (IOException e) {
                    results.add(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onDrain() {
                results.add("SENT SYN FROM CLIENT");
                try {
                    socket.shutdownOutput();
                } catch (IOException e) {
                    results.add(e.getMessage());
                }
            }

            @Override
            public void onClose() {
                results.add("CLOSING CONNECTION FROM CLIENT SIDE");
            }

            @Override
            public void onError(Exception e) {
                results.add(e);
                e.printStackTrace();
            }
        }));
        EVENT_LOOP.defer(EVENT_LOOP::stop, 5000);
        EVENT_LOOP.run();
        results.add("CLOSED SERVER SOCKET");
        assertEquals(
                Arrays.asList(
                        "STARTED SERVER SOCKET",
                        "CONNECTED TO SERVER",
                        "ACCEPTED CONNECTION FROM CLIENT",
                        "CONNECTED TO CLIENT",
                        "SENT SYN FROM CLIENT",
                        "RECEIVED SYN FROM CLIENT",
                        "SENT ACK FROM SERVER",
                        "RECEIVED ACK FROM SERVER",
                        "CLOSING CONNECTION FROM CLIENT SIDE",
                        "CLOSING CONNECTION FROM SERVER SIDE",
                        "CLOSING SERVER SOCKET",
                        "CLOSED SERVER SOCKET"
                ),
                results
        );
    }
}