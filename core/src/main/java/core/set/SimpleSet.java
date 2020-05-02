package core.set;

import core.Enumerable;
import core.map.Map;

import java.util.function.Consumer;

public abstract class SimpleSet<E> implements Set<E>, Enumerable<E> {
    private transient Map<E, Object> map;
    private static final Object PRESENT = new Object();

    protected SimpleSet(Map<E, Object> map) {
        this.map = map;
    }

    public void add(E e) {
        this.map.link(e, PRESENT);
    }

    public void clear() {
        this.map.clear();
    }

    @Override
    public boolean contains(E element) {
        return this.map.map(element) == PRESENT;
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        this.map.forEach(consumer);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void remove(E element) {
        this.map.remove(element);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof SimpleSet))
            return false;
        SimpleSet<E> s = (SimpleSet<E>) o;
        return this.map.equals(s.map);
    }

    public int hashCode() {
        return this.map.hashCode();
    }
}
