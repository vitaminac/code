package core.map;

import java.util.function.Consumer;

public class SeparateChainingHashTableMap<Key, Value> extends AbstractHashMap<Key, Value, SeparateChainingHashTableMap.Entry<Key, Value>> {
    public static class Entry<Key, Value> extends Relation<Key, Value> {
        private Entry<Key, Value> next;

        public Entry(Key key, Value value) {
            super(key, value);
        }
    }

    @Override
    public void put(Key key, Value value) {
        int index = this.compress(this.hash(key));
        var current = this.entries[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
            current = current.next;
        }
        var newEntry = new Entry<>(key, value);
        newEntry.next = this.entries[index];
        this.entries[index] = newEntry;
        this.size += 1;
        if (this.size > this.capacity * LOAD_FACTOR) this.resize(this.capacity * 2);
    }

    @Override
    public Value get(Key key) {
        int index = this.compress(this.hash(key));
        if (this.entries[index] != null) {
            var current = this.entries[index];
            while (current != null) {
                if (current.getKey().equals(key)) {
                    return current.getValue();
                }
                current = current.next;
            }
        }
        return null;
    }

    @Override
    public Value remove(Key key) {
        int index = this.compress(this.hash(key));
        var current = this.entries[index];
        Value retVal = null;
        if (current != null) {
            if (current.getKey().equals(key)) {
                retVal = current.getValue();
                this.entries[index] = current.next;
                this.size -= 1;
            } else {
                while (current.next != null) {
                    if (current.next.getKey().equals(key)) {
                        retVal = current.next.getValue();
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
        return retVal;
    }

    @Override
    public void forEach(Consumer<? super Key> consumer) {
        for (Entry<Key, Value> entry : this.entries) {
            while (entry != null) {
                consumer.accept(entry.getKey());
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
                this.put(current.getKey(), current.getValue());
                current = current.next;
            }
        }
    }
}
