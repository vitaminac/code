package test;

public final class Asserts {
    public static void assertEquals(final Object expected, final Object actual) {
        assertEquals(expected, actual, expected + " is not equals to " + actual);
    }

    public static void assertEquals(final Object expected, final Object actual, final String msg) {
        if (expected == null && actual == null) return;
        if (expected == null || actual == null) throw new AssertionError(msg);
        if (expected.equals(actual)) return;
        throw new AssertionError(msg);
    }

    public static <T extends Throwable> void assertThrows(final Runnable runnable, final Class<T> exClass) {
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
