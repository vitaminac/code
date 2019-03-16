package code.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements Bag<E>, Queue<E>, Stack<E>, PositionList<E>, Deque<E> {
    private static class LinkedNode<E> implements Position<E> {
        public E element;
        public LinkedNode<E> next;
        public LinkedNode<E> prev;

        public LinkedNode(E element) {
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
        for (E item : iterable) this.addLast(item);
    }

    public LinkedList(Stack<E> stack) {
        while (!stack.isEmpty()) this.push(stack.pop());
    }

    public LinkedList(Queue<E> queue) {
        while (!queue.isEmpty()) this.enqueue(queue.dequeue());
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
        if (this.isEmpty()) this.last = node;
        else {
            this.first.prev = node;
            node.next = this.first;
        }
        this.first = node;
    }

    @Override
    public void addLast(E element) {
        LinkedNode<E> node = new LinkedNode<E>(element);
        if (this.isEmpty()) this.first = node;
        else {
            this.last.next = node;
            node.prev = this.last;
        }
        this.last = node;
    }

    @Override
    public E removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();
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
        if (this.isEmpty()) throw new NoSuchElementException();
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
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.firstPosition().getElement();
    }

    @Override
    public E last() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.lastPosition().getElement();
    }

    @Override
    public Position<E> firstPosition() {
        return this.first;
    }

    @Override
    public Position<E> lastPosition() {
        return this.last;
    }

    @Override
    public Position<E> before(Position<E> position) {
        if (position.getClass().equals(LinkedNode.class)) {
            return ((LinkedNode<E>) position).prev;
        } else throw new IllegalArgumentException();
    }

    @Override
    public Position<E> after(Position<E> position) {
        if (position.getClass().equals(LinkedNode.class)) {
            return ((LinkedNode<E>) position).next;
        } else throw new IllegalArgumentException();
    }

    @Override
    public Position<E> insertBefore(Position<E> position, E element) {
        if (position.getClass().equals(LinkedNode.class)) {
            LinkedNode<E> node = new LinkedNode<E>(element);
            LinkedNode<E> next = (LinkedNode<E>) position;
            next.prev = node;
            node.next = next;
            return node;
        } else throw new IllegalArgumentException();
    }

    @Override
    public Position<E> insertAfter(Position<E> position, E element) {
        if (position.getClass().equals(LinkedNode.class)) {
            LinkedNode<E> node = new LinkedNode<E>(element);
            LinkedNode<E> prev = (LinkedNode<E>) position;
            prev.next = node;
            node.prev = prev;
            return node;
        } else throw new IllegalArgumentException();
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
}
