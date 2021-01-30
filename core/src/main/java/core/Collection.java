package core;

public interface Collection<E> extends Enumerable<E> {
    // TODO: remove
    default int size() {
        int[] ref = new int[]{0};
        this.forEach(e -> ref[0]++);
        return ref[0];
    }
}
