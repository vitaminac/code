package code.adt.dict;

import code.adt.LinkedList;
import code.adt.Stack;

import java.util.function.Consumer;

public class SkipList<Key extends Comparable<Key>, Value> implements Dictionary<Key, Value> {
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

    private final Vocabulary<Key, Value> MIN_SENTIMENTAL = new Vocabulary<>(null, null) {
        @Override
        public int compareTo(Key key) {
            return -1;
        }
    };

    private Node<Vocabulary<Key, Value>> top = new Node<>(MIN_SENTIMENTAL);

    @Override
    public int size() {
        Node<Vocabulary<Key, Value>> current = this.top;
        while (current.down != null) {
            current = current.down;
        }
        int size = 0;
        while (current.next != null) {
            current = current.next;
            size += 1;
        }
        return size;
    }

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
    public void link(Key key, Value value) {
        this.link(this.top, new Vocabulary<>(key, value));
        if (this.top.next != null) {
            var newTop = new Node<>(MIN_SENTIMENTAL);
            newTop.down = this.top;
            this.top = newTop;
        }
    }

    @Override
    public Value map(Key key) {
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
    public Value remove(Key key) {
        Node<Vocabulary<Key, Value>> current = this.top;
        int diff = -1;
        Value result = null;
        while (current.down != null) {
            current = current.down;
            while (current.next != null && (diff = current.next.element.compareTo(key)) < 0) {
                current = current.next;
            }
            if (diff == 0) {
                result = current.next.element.getValue();
                current.next = current.next.next;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        this.top = new Node<>(MIN_SENTIMENTAL);
    }

    @Override
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
    public void enumerate(Consumer<Key> consumer) {
        Node<Vocabulary<Key, Value>> current = this.top;
        while (current.down != null) current = current.down;
        current = current.next;
        do {
            consumer.accept(current.element.getKey());
            current = current.next;
        } while (current != null);
    }
}
