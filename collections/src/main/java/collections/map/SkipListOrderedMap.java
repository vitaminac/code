package collections.map;

import java.util.function.Consumer;

public class SkipListOrderedMap<Key extends Comparable<? super Key>, Value>
        implements MutableMap<Key, Value>, OrderedMap<Key, Value> {
    private static class SkipListNode<E> {
        private E element;
        private SkipListNode<E> next;
        private SkipListNode<E> down;

        private SkipListNode(E element) {
            this.element = element;
        }

        @Override
        public String toString() {
            return "Element:" + element + "; Next: " + (next == null ? null : next.element) + "; Down:" + (down == null ? null : down.element);
        }
    }

    private static class Vocabulary<Key extends Comparable<? super Key>, Value>
            extends Relation<Key, Value>
            implements Comparable<Key> {
        public Vocabulary(Key key, Value value) {
            super(key, value);
        }

        @Override
        public int compareTo(final Key key) {
            return this.getKey().compareTo(key);
        }
    }


    private final Vocabulary<Key, Value> MIN_SENTIMENTAL = new Vocabulary<>(null, null) {
        @Override
        public int compareTo(Key key) {
            return -1;
        }
    };

    private SkipListNode<Vocabulary<Key, Value>> top = new SkipListNode<>(MIN_SENTIMENTAL);

    @Override
    public boolean isEmpty() {
        return this.top.down == null;
    }

    @Override
    public int size() {
        int result = 0;
        var current = this.top;
        while (current.down != null) current = current.down;
        current = current.next;
        while (current != null) {
            result += 1;
            current = current.next;
        }
        return result;
    }

    // TODO: move to OrderedMap
    public Key firstKey() {
        var current = top;
        while (current.down != null) {
            current = current.down;
        }
        return current.next.element.getKey();
    }

    private SkipListNode<Vocabulary<Key, Value>> link(SkipListNode<Vocabulary<Key, Value>> skipListNode, Vocabulary<Key, Value> vocabulary) {
        if (skipListNode == null) return new SkipListNode<>(vocabulary);
        var current = skipListNode;
        int diff = current.element.compareTo(vocabulary.getKey());
        while (current.next != null && (diff = current.next.element.compareTo(vocabulary.getKey())) < 0) {
            current = current.next;
        }
        if (diff == 0) {
            current = current.next;
            while (current != null) {
                current.element = vocabulary;
                current = current.down;
            }
        } else {
            SkipListNode<Vocabulary<Key, Value>> newSkipListNode = this.link(skipListNode.down, vocabulary);
            if (newSkipListNode != null) {
                newSkipListNode.next = current.next;
                current.next = newSkipListNode;
                if (Math.random() > 0.5) {
                    var retVal = new SkipListNode<>(vocabulary);
                    retVal.down = newSkipListNode;
                    return retVal;
                }
            }
        }
        return null;
    }

    @Override
    public void put(Key domain, Value coDomain) {
        this.link(this.top, new Vocabulary<>(domain, coDomain));
        if (this.top.next != null) {
            var newTop = new SkipListNode<>(MIN_SENTIMENTAL);
            newTop.down = this.top;
            this.top = newTop;
        }
    }

    @Override
    public Value get(Key key) {
        SkipListNode<Vocabulary<Key, Value>> current = this.top;
        while (current.down != null) {
            current = current.down;
            int diff = -1;
            while (current.next != null && (diff = current.next.element.compareTo(key)) < 0) {
                current = current.next;
            }
            if (diff == 0) {
                return current.next.element.getValue();
            }
        }
        return null;
    }

    @Override
    public void remove(Key domain) {
        SkipListNode<Vocabulary<Key, Value>> current = this.top;
        int diff = -1;
        Value result = null;
        while (current.down != null) {
            current = current.down;
            while (current.next != null && (diff = current.next.element.compareTo(domain)) < 0) {
                current = current.next;
            }
            if (diff == 0) {
                current.next = current.next.next;
            }
        }
    }

    public void findRange(Key min, Key max, Consumer<Key> consumer) {
        SkipListNode<Vocabulary<Key, Value>> current = this.top;
        int diff = -1;
        while (current.down != null) {
            current = current.down;
            while (current.next != null && (diff = current.next.element.compareTo(min)) <= 0) {
                current = current.next;
            }
        }
        if (diff >= 0) {
            do {
                consumer.accept(current.element.getKey());
                current = current.next;
            } while (current != null && current.element.compareTo(max) <= 0);
        }
    }

    @Override
    public void enumerate(Consumer<? super Key> consumer) {
        SkipListNode<Vocabulary<Key, Value>> current = this.top;
        while (current.down != null) current = current.down;
        current = current.next;
        while (current != null) {
            consumer.accept(current.element.getKey());
            current = current.next;
        }
    }

    @Override // TODO: TO BE REMOVED
    public void clear() {
        this.top = new SkipListNode<>(MIN_SENTIMENTAL);
    }
}
