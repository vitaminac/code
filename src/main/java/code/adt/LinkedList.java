package code.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E>
        implements Bag<E>, Queue<E>, Stack<E>, PositionList<E>, Deque<E>, Cloneable {
    public static class LinkedNode<E> implements Position<E> {
        private E element;
        private LinkedNode<E> next;
        private LinkedNode<E> prev;

        private LinkedNode(E element) {
            this.element = element;
        }

        @Override
        public E getElement() {
            return this.element;
        }
    }

    private LinkedNode<E> first;
    private LinkedNode<E> last;

    public LinkedList() {
    }

    public LinkedList(Iterable<E> iterable) {
        for (E item : iterable)
            this.addLast(item);
    }

    @Override
    public void add(E item) {
        this.addLast(item);
    }

    @Override
    public boolean isEmpty() {
        return this.first == null;
    }

    @Override
    public void addFirst(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty())
            this.last = node;
        else {
            this.first.prev = node;
            node.next = this.first;
        }
        this.first = node;
    }

    @Override
    public void addLast(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty())
            this.first = node;
        else {
            this.last.next = node;
            node.prev = this.last;
        }
        this.last = node;
    }

    @Override
    public E removeFirst() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        E element = this.first.getElement();
        if (this.first == this.last) {
            this.first = null;
            this.last = null;
        } else {
            this.first = this.first.next;
            this.first.prev = null;
        }
        return element;
    }

    @Override
    public E removeLast() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        E element = this.last.getElement();
        if (this.first == this.last) {
            this.first = null;
            this.last = null;
        } else {
            this.last = this.last.prev;
            this.last.next = null;
        }
        return element;
    }

    @Override
    public E first() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.firstPosition().getElement();
    }

    @Override
    public E last() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        return this.lastPosition().getElement();
    }

    @Override
    public LinkedNode<E> firstPosition() {
        return this.first;
    }

    @Override
    public LinkedNode<E> lastPosition() {
        return this.last;
    }

    private LinkedNode<E> checkPosition(Position<E> position) {
        if (position instanceof LinkedNode) {
            return (LinkedNode<E>) position;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public LinkedNode<E> before(Position<E> p) {
        LinkedNode<E> position = this.checkPosition(p);
        return position.prev;
    }

    @Override
    public LinkedNode<E> after(Position<E> p) {
        LinkedNode<E> position = this.checkPosition(p);
        return position.next;
    }

    @Override
    public LinkedNode<E> insertBefore(Position<E> p, E element) {
        LinkedNode<E> position = this.checkPosition(p);
        LinkedNode<E> node = new LinkedNode<E>(element);
        position.prev = node;
        node.next = position;
        return node;
    }

    @Override
    public LinkedNode<E> insertAfter(Position<E> p, E element) {
        LinkedNode<E> position = this.checkPosition(p);
        LinkedNode<E> node = new LinkedNode<E>(element);
        position.next = node;
        node.prev = position;
        return node;
    }

    @Override
    public E peek() {
        return this.first();
    }

    @Override
    public void push(E element) {
        this.addFirst(element);
    }

    @Override
    public E pop() {
        return this.removeFirst();
    }

    @Override
    public void enqueue(E element) {
        this.addLast(element);
    }

    @Override
    public E dequeue() {
        return this.removeFirst();
    }

    @Override
    public int size() {
        int size = 0;
        LinkedNode<E> node = this.first;
        while (node != null) {
            ++size;
            node = node.next;
        }
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Iterable)) return false;
        Iterator<Object> it = ((Iterable<Object>) o).iterator();
        Iterator<E> iterator = this.iterator();
        while (it.hasNext() && iterator.hasNext()) {
            if (!it.next().equals(iterator.next())) return false;
        }
        return !it.hasNext() && !iterator.hasNext();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private LinkedNode<E> next = LinkedList.this.first;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public E next() {
                E item = next.element;
                this.next = next.next;
                return item;
            }
        };
    }

    @Override
    public LinkedList<E> clone() {
        LinkedList<E> list = new LinkedList<>();
        for (E e : this) {
            list.add(e);
        }
        return list;
    }

    public LinkedList<E> concatenate(Iterable<E> iterable) {
        LinkedList<E> list = new LinkedList<>();
        for (E e : this) {
            list.add(e);
        }
        for (E e : iterable) {
            list.add(e);
        }
        return list;
    }
}
