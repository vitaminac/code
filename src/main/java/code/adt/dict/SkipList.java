package code.adt.dict;

import code.adt.LinkedList;
import code.adt.Stack;
import code.adt.map.Relation;

import java.util.function.Consumer;

public class SkipList<Key extends Comparable<Key>, Value> implements Dictionary<Key, Value> {
    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> down;

        private Node(E element) {
            this.element = element;
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

    @Override
    public void link(Key key, Value value) {
        Node<Vocabulary<Key, Value>> current = this.top;
        Stack<Node<Vocabulary<Key, Value>>> stack = new LinkedList<>();
        while (current.down != null) {
            stack.push(current);
            current = current.down;
            while (current.next != null && current.next.element.compareTo(key) <= 0) {
                current = current.next;
            }
        }
        double flag = Math.random();
        int diff = current.element.compareTo(key);
        if (diff == 0) {
            do {
                current.element = new Vocabulary<>(key, value);
            } while (!stack.isEmpty() && (current = stack.pop()) != null);
        } else {
            Node<Vocabulary<Key, Value>> down = null;
            do {
                Node<Vocabulary<Key, Value>> node = new Node<>(new Vocabulary<>(key, value));
                node.next = current.next;
                current.next = node;
                node.down = down;
                down = node;
            } while (!stack.isEmpty() && (current = stack.pop()) != null && ((flag *= 2) < 0.5));

        }
        if (current == this.top) {
            Node<Vocabulary<Key, Value>> newTop = new Node<>(MIN_SENTIMENTAL);
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
            while (current.next != null && current.next.element.compareTo(max) <= 0) {
                current = current.next;
                consumer.accept(current.element.getKey());
            }
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
