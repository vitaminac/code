package test;

public final class Asserts {
    public static void assertEquals(Object expected, Object actual, String msg) {
        if (expected == null && actual == null) return;
        if (expected == null || actual == null) throw new AssertionError(expected + " is not equals to " + actual);
        if (expected.equals(actual)) return;
        throw new AssertionError(msg);
    }

    public static <T extends Throwable> void assertThrows(Runnable runnable, Class<T> exClass) {
        try {
            runnable.run();
        } catch (final Throwable e) {
            if (exClass.isInstance(e)) {
                return;
            }
        }
        throw new AssertionError("expected " + exClass.getName() + " but wasn't thrown");
    }
}
