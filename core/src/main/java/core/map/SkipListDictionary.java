package core.map;

import java.util.function.Consumer;

public class SkipListDictionary<Key extends Comparable<? super Key>, Value>
        implements OrderedMap<Key, Value> {
    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> down;

        private Node(E element) {
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

    private Node<Vocabulary<Key, Value>> top = new Node<>(MIN_SENTIMENTAL);

    @Override
    public boolean isEmpty() {
        return this.top.down == null;
    }

    private Node<Vocabulary<Key, Value>> link(Node<Vocabulary<Key, Value>> node, Vocabulary<Key, Value> vocabulary) {
        if (node == null) return new Node<>(vocabulary);
        var current = node;
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
            Node<Vocabulary<Key, Value>> newNode = this.link(node.down, vocabulary);
            if (newNode != null) {
                newNode.next = current.next;
                current.next = newNode;
                if (Math.random() > 0.5) {
                    var retVal = new Node<>(vocabulary);
                    retVal.down = newNode;
                    return retVal;
                }
            }
        }
        return null;
    }

    @Override
    public void put(Key key, Value value) {
        this.link(this.top, new Vocabulary<>(key, value));
        if (this.top.next != null) {
            var newTop = new Node<>(MIN_SENTIMENTAL);
            newTop.down = this.top;
            this.top = newTop;
        }
    }

    @Override
    public Value get(Key key) {
        Node<Vocabulary<Key, Value>> current = this.top;
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
    public void remove(Key key) {
        Node<Vocabulary<Key, Value>> current = this.top;
        int diff = -1;
        Value result = null;
        while (current.down != null) {
            current = current.down;
            while (current.next != null && (diff = current.next.element.compareTo(key)) < 0) {
                current = current.next;
            }
            if (diff == 0) {
                current.next = current.next.next;
            }
        }
    }

    public void findRange(Key min, Key max, Consumer<Key> consumer) {
        Node<Vocabulary<Key, Value>> current = this.top;
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
        Node<Vocabulary<Key, Value>> current = this.top;
        while (current.down != null) current = current.down;
        current = current.next;
        while (current != null) {
            consumer.accept(current.element.getKey());
            current = current.next;
        }
    }

    @Override // TODO: TO BE REMOVED
    public void clear() {
        this.top = new Node<>(MIN_SENTIMENTAL);
    }
}
