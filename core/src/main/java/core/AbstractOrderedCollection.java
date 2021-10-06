package core;

import java.util.Iterator;

public abstract class AbstractOrderedCollection<E> implements Collection<E> {
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Iterable)) return false;
        @SuppressWarnings("unchecked")
        Iterator<Object> it = ((Iterable<Object>) o).iterator();
        Iterator<E> iterator = this.iterator();
        while (it.hasNext() && iterator.hasNext()) {
            if (!it.next().equals(iterator.next())) return false;
        }
        return !it.hasNext() && !iterator.hasNext();
    }
}
