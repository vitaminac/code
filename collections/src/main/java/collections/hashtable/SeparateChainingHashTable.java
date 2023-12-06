package collections.hashtable;

import collections.linkedlist.SinglyLinkedListNode;

import java.util.function.Consumer;

public class SeparateChainingHashTable<Item>
        extends AbstractHashTable<Item> {
    private SinglyLinkedListNode<Item>[] buckets;

    @Override
    public void put(final Item item) {
        int bucketIndex = this.compress(this.hash(item));
        var current = this.buckets[bucketIndex];
        if (current == null) {
            addNewEntry(item, bucketIndex);
        } else if (current.getElement().equals(item)) {
            this.buckets[bucketIndex] = new SinglyLinkedListNode<>(item);
        } else {
            var prev = current;
            while ((current = current.getNext()) != null) {
                if (current.getElement().equals(item)) {
                    var next = current.getNext();
                    final var newEntry = new SinglyLinkedListNode<>(item);
                    prev.setNext(newEntry);
                    newEntry.setNext(next);
                    return;
                }
                prev = current;
            }
            addNewEntry(item, bucketIndex);
        }
    }

    private void addNewEntry(final Item item, final int bucketIndex) {
        var newEntry = new SinglyLinkedListNode<>(item);
        newEntry.setNext(this.buckets[bucketIndex]);
        this.buckets[bucketIndex] = newEntry;
        this.size += 1;
        if (this.size > this.capacity * LOAD_FACTOR) this.resize(this.capacity * 2);
    }

    @Override
    public Item get(final Item hint) {
        int index = this.compress(this.hash(hint));
        if (this.buckets[index] != null) {
            var current = this.buckets[index];
            while (current != null) {
                if (current.getElement().equals(hint)) {
                    return current.getElement();
                }
                current = current.getNext();
            }
        }
        return null;
    }

    @Override
    public void remove(Item hint) {
        int index = this.compress(this.hash(hint));
        var current = this.buckets[index];
        if (current != null) {
            if (current.getElement().equals(hint)) {
                this.buckets[index] = current.getNext();
                this.size -= 1;
            } else {
                while (current.getNext() != null) {
                    if (current.getNext().getElement().equals(hint)) {
                        current.setNext(current.getNext().getNext());
                        this.size -= 1;
                        break;
                    }
                    current = current.getNext();
                }
            }
        }
        if (this.size < this.capacity * LOAD_FACTOR * LOAD_FACTOR * LOAD_FACTOR && this.capacity > DEFAULT_INITIAL_CAPACITY)
            this.resize(this.capacity / 2);
    }

    @Override
    public void enumerate(Consumer<? super Item> consumer) {
        for (SinglyLinkedListNode<Item> entry : this.buckets) {
            while (entry != null) {
                consumer.accept(entry.getElement());
                entry = entry.getNext();
            }
        }
    }


    @Override
    protected void init(int capacity) {
        this.capacity = capacity;
        @SuppressWarnings("unchecked")
        final SinglyLinkedListNode<Item>[] entries = new SinglyLinkedListNode[this.capacity];
        this.buckets = entries;
        this.size = 0;
    }

    private void resize(int capacity) {
        var old = this.buckets;
        this.init(capacity);
        for (var current : old) {
            while (current != null) {
                this.put(current.getElement());
                current = current.getNext();
            }
        }
    }
}
