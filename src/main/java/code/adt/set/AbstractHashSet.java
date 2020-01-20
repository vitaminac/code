package code.adt.set;

import code.adt.map.Map;

import java.util.Iterator;
import java.util.function.Consumer;

public abstract class AbstractHashSet<E> implements Set<E> {
    private transient Map<E, Object> map;
    private static final Object PRESENT = new Object();

    protected AbstractHashSet(Map<E, Object> map) {
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
    public void enumerate(Consumer<? super E> consumer) {
        this.map.enumerate(consumer);
    }

    @Override
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

        if (!(o instanceof java.util.Set))
            return false;
        Set<E> s = (Set<E>) o;
        if (s.size() != size())
            return false;
        for (E e : this) {
            if (!s.contains(e)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E obj = it.next();
            if (obj != null)
                h += obj.hashCode();
        }
        return h;
    }
}
