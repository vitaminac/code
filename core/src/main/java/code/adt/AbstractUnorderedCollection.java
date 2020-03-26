package code.adt;

import java.util.Set;

public abstract class AbstractUnorderedCollection<E> implements Set<E> {
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof Set)) return false;
        Set<Object> s = ((Set<Object>) o);
        if (this.size() != s.size()) return false;
        for (Object e : s) {
            if (!this.contains(e)) return false;
        }
        return true;
    }
}
