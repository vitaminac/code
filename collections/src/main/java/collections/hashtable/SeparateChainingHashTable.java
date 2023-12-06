package collections.hashtable;

import collections.linkedlist.SinglyLinkedListNode;
import collections.map.Relation;

import java.util.function.Consumer;

public class SeparateChainingHashTable<Key, Value> extends AbstractHashTable<Key, Value, SinglyLinkedListNode<Relation<Key, Value>>> {
    @Override
    public void put(final Key key, final Value value) {
        int bucketIndex = this.compress(this.hash(key));
        var current = this.entries[bucketIndex];
        if (current == null) {
            addNewEntry(key, value, bucketIndex);
        } else if (current.getElement().getKey().equals(key)) {
            this.entries[bucketIndex] = new SinglyLinkedListNode<>(new Relation<>(key, value));
        } else {
            var prev = current;
            while ((current = current.getNext()) != null) {
                if (current.getElement().getKey().equals(key)) {
                    var next = current.getNext();
                    final var newEntry = new SinglyLinkedListNode<>(new Relation<>(key, value));
                    prev.setNext(newEntry);
                    newEntry.setNext(next);
                    return;
                }
                prev = current;
            }
            addNewEntry(key, value, bucketIndex);
        }
    }

    private void addNewEntry(final Key key, final Value value, final int bucketIndex) {
        var newEntry = new SinglyLinkedListNode<>(new Relation<>(key, value));
        newEntry.setNext(this.entries[bucketIndex]);
        this.entries[bucketIndex] = newEntry;
        this.size += 1;
        if (this.size > this.capacity * LOAD_FACTOR) this.resize(this.capacity * 2);
    }

    @Override
    public Value get(Key key) {
        int index = this.compress(this.hash(key));
        if (this.entries[index] != null) {
            var current = this.entries[index];
            while (current != null) {
                if (current.getElement().getKey().equals(key)) {
                    return current.getElement().getValue();
                }
                current = current.getNext();
            }
        }
        return null;
    }

    @Override
    public void remove(Key key) {
        int index = this.compress(this.hash(key));
        var current = this.entries[index];
        if (current != null) {
            if (current.getElement().getKey().equals(key)) {
                this.entries[index] = current.getNext();
                this.size -= 1;
            } else {
                while (current.getNext() != null) {
                    if (current.getNext().getElement().getKey().equals(key)) {
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
    public void enumerate(Consumer<? super Key> consumer) {
        for (SinglyLinkedListNode<Relation<Key, Value>> entry : this.entries) {
            while (entry != null) {
                consumer.accept(entry.getElement().getKey());
                entry = entry.getNext();
            }
        }
    }


    @Override
    protected void init(int capacity) {
        this.capacity = capacity;
        @SuppressWarnings("unchecked")
        final SinglyLinkedListNode<Relation<Key, Value>>[] entries = new SinglyLinkedListNode[this.capacity];
        this.entries = entries;
        this.size = 0;
    }

    private void resize(int capacity) {
        var old = this.entries;
        this.init(capacity);
        for (var current : old) {
            while (current != null) {
                this.put(current.getElement().getKey(), current.getElement().getValue());
                current = current.getNext();
            }
        }
    }
}
