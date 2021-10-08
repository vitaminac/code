package core.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Iterators {
    private Iterators() {
    }

    public static <E> Iterator<E> from(final E[] arr) {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < arr.length;
            }

            @Override
            public E next() {
                if (this.index >= arr.length) throw new NoSuchElementException();
                return arr[this.index++];
            }
        };
    }

    @SafeVarargs
    public static <E> Iterator<E> flatten(final Iterator<? extends E>... iterators) {
        return new Iterator<>() {
            private int index = 0;

            private Iterator<? extends E> nextIt() {
                while (this.index < iterators.length && !iterators[this.index].hasNext()) ++this.index;
                return this.index < iterators.length ? iterators[this.index] : null;
            }

            @Override
            public boolean hasNext() {
                return nextIt() != null;
            }

            @Override
            public E next() {
                final Iterator<? extends E> it = nextIt();
                if (it == null) throw new NoSuchElementException();
                return it.next();
            }
        };
    }
}
