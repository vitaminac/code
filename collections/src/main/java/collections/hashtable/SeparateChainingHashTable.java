package collections.hashtable;

import collections.map.Relation;

import java.util.function.Consumer;

public class SeparateChainingHashTable<Key, Value> extends AbstractHashTable<Key, Value, SeparateChainingHashTable.Entry<Key, Value>> {
    public static class Entry<Key, Value> {
        private final Relation<Key, Value> value;
        private Entry<Key, Value> next;

        public Entry(final Key key, final Value value) {
            this.value = new Relation<>(key, value);
        }
    }

    @Override
    public void put(final Key key, final Value value) {
        int bucketIndex = this.compress(this.hash(key));
        var current = this.entries[bucketIndex];
        if (current == null) {
            addNewEntry(key, value, bucketIndex);
        } else if (current.value.getKey().equals(key)) {
            this.entries[bucketIndex] = new Entry<>(key, value);
        } else {
            var prev = current;
            while ((current = current.next) != null) {
                if (current.value.getKey().equals(key)) {
                    var next = current.next;
                    final var newEntry = new Entry<>(key, value);
                    prev.next = newEntry;
                    newEntry.next = next;
                    return;
                }
                prev = current;
            }
            addNewEntry(key, value, bucketIndex);
        }
    }

    private void addNewEntry(final Key key, final Value value, final int bucketIndex) {
        var newEntry = new Entry<>(key, value);
        newEntry.next = this.entries[bucketIndex];
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
                if (current.value.getKey().equals(key)) {
                    return current.value.getValue();
                }
                current = current.next;
            }
        }
        return null;
    }

    @Override
    public void remove(Key key) {
        int index = this.compress(this.hash(key));
        var current = this.entries[index];
        if (current != null) {
            if (current.value.getKey().equals(key)) {
                this.entries[index] = current.next;
                this.size -= 1;
            } else {
                while (current.next != null) {
                    if (current.next.value.getKey().equals(key)) {
                        current.next = current.next.next;
                        this.size -= 1;
                        break;
                    }
                    current = current.next;
                }
            }
        }
        if (this.size < this.capacity * LOAD_FACTOR * LOAD_FACTOR * LOAD_FACTOR && this.capacity > DEFAULT_INITIAL_CAPACITY)
            this.resize(this.capacity / 2);
    }

    @Override
    public void enumerate(Consumer<? super Key> consumer) {
        for (Entry<Key, Value> entry : this.entries) {
            while (entry != null) {
                consumer.accept(entry.value.getKey());
                entry = entry.next;
            }
        }
    }


    @Override
    protected void init(int capacity) {
        this.capacity = capacity;
        @SuppressWarnings("unchecked")
        Entry<Key, Value>[] entries = new Entry[this.capacity];
        this.entries = entries;
        this.size = 0;
    }

    private void resize(int capacity) {
        var old = this.entries;
        this.init(capacity);
        for (var current : old) {
            while (current != null) {
                this.put(current.value.getKey(), current.value.getValue());
                current = current.next;
            }
        }
    }
}
