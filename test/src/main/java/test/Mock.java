package test;

import core.Function;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Supplier;

import static core.Reflections.defaultValueOf;
import static test.Asserts.assertEquals;

public class Mock {
    private static class MockInvocation {
        private final Method method;
        private final Object[] args;
        private final Function<Void, Object, Throwable> reaction;

        private MockInvocation(final Method method, final Object[] args, final Function<Void, Object, Throwable> reaction) {
            this.method = method;
            this.args = args;
            this.reaction = reaction;
        }
    }

    public static void finishPreparing(Object... mocks) {
        for (var mock : mocks) {
            final var handler = (MockInvocationHandler) Proxy.getInvocationHandler(mock);
            handler.prepared = true;
        }
    }

    private static class MockInvocationHandler implements InvocationHandler {
        private Method lastMethod;
        private Object[] lastArgs;
        private final Queue<MockInvocation> expectedInvocations = new LinkedList<>();
        private boolean prepared = false;

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            if (this.prepared) {
                final var invocation = this.expectedInvocations.remove();
                if (Objects.equals(invocation.method, method) && Arrays.deepEquals(invocation.args, args)) {
                    if (method.getReturnType().equals(void.class)) {
                        return null;
                    } else {
                        return invocation.reaction.callback(null);
                    }
                } else {
                    throw new RuntimeException("mismatch invocation for method " + method.getName() + " and arguments " + Arrays.deepToString(args));
                }
            } else {
                if (method.getReturnType().equals(void.class)) {
                    this.expectedInvocations.add(new MockInvocation(method, args, null));
                } else {
                    this.lastMethod = method;
                    this.lastArgs = args;
                }
                return defaultValueOf(method.getReturnType());
            }
        }

        private void finishInvocation(final Function<Void, Object, Throwable> reaction) {
            this.expectedInvocations.add(new MockInvocation(this.lastMethod, this.lastArgs, reaction));
        }
    }

    private static MockInvocationHandler lastMockInvocationHandler;

    public static <T> T mock(Class<T> proxyClass) {
        lastMockInvocationHandler = new MockInvocationHandler();
        @SuppressWarnings("unchecked") final T proxy = (T) Proxy.newProxyInstance(null, new Class<?>[]{proxyClass}, lastMockInvocationHandler);
        return proxy;
    }

    public interface MockReturnValue<T> {
        void thenReturn(T returnVal);

        void thenThrow(Supplier<? extends Throwable> supplier);
    }

    public static <T> MockReturnValue<T> when(T ignore) {
        return new MockReturnValue<>() {
            @Override
            public void thenReturn(T returnVal) {
                lastMockInvocationHandler.finishInvocation((ignore) -> returnVal);
            }

            @Override
            public void thenThrow(Supplier<? extends Throwable> supplier) {
                lastMockInvocationHandler.finishInvocation((ignore) -> {
                    throw supplier.get();
                });
            }
        };
    }

    public static <T> T verify(T mock) {
        return mock;
    }

    public static void verifyExpectedInteractions(Object... mocks) {
        for (var mock : mocks) {
            var handler = (MockInvocationHandler) Proxy.getInvocationHandler(mock);
            assertEquals(0, handler.expectedInvocations.size(), "Expected all invocations to be consumed");
        }
    }
}
