package code.adt.dict;

import java.util.function.Consumer;

/**
 * https://www.cl.cam.ac.uk/techreports/UCAM-CL-TR-579.pdf
 * http://www.cse.chalmers.se/~tsigas/papers/SLIDES/Lock-free%20Skip%20Lists%20and%20Priority%20Queues.ppt
 * http://www.cs.tau.ac.il/~shanir/nir-pubs-web/Papers/OPODIS2006-BA.pdf
 * TODO: ConcurrentSkipListMap
 */
public class ConcurrentSkipListMap<K extends Comparable<K>, V> implements Dictionary<K, V> {
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

    private static class IndexNode<E> {

    }

    private final Vocabulary<K, V> MIN_SENTIMENTAL = new Vocabulary<>(null, null) {
        @Override
        public int compareTo(K key) {
            return -1;
        }
    };

    private Node<Vocabulary<K, V>> top = new Node<>(MIN_SENTIMENTAL);

    @Override
    public boolean isEmpty() {
        return this.top.down == null;
    }

    private Node<Vocabulary<K, V>> link(Node<Vocabulary<K, V>> node, Vocabulary<K, V> vocabulary) {
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
            Node<Vocabulary<K, V>> newNode = this.link(node.down, vocabulary);
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
    public void link(K key, V value) {
        this.link(this.top, new Vocabulary<>(key, value));
        if (this.top.next != null) {
            var newTop = new Node<>(MIN_SENTIMENTAL);
            newTop.down = this.top;
            this.top = newTop;
        }
    }

    @Override
    public V map(K key) {
        Node<Vocabulary<K, V>> current = this.top;
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
    public V remove(K key) {
        Node<Vocabulary<K, V>> current = this.top;
        int diff = -1;
        V result = null;
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
    public void findRange(K min, K max, Consumer<K> consumer) {
        Node<Vocabulary<K, V>> current = this.top;
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
    public void forEach(Consumer<? super K> consumer) {
        Node<Vocabulary<K, V>> current = this.top;
        while (current.down != null) current = current.down;
        current = current.next;
        while (current != null) {
            consumer.accept(current.element.getKey());
            current = current.next;
        }
    }
}
