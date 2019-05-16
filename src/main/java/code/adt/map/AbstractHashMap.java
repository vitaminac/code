package code.adt.map;

import code.adt.Relation;

import java.util.function.Consumer;

public abstract class AbstractHashMap<Key, Value> implements Map<Key, Value> {
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final double LAMBDA = 0.75;
    protected int capacity;
    private int size;
    private Relation<Key, Value>[] relations;

    @SuppressWarnings("unchecked")
    public AbstractHashMap() {
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.relations = new Relation[this.capacity];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void link(Key key, Value value) {
        if (((double) this.capacity) / this.size > LAMBDA) this.resize(this.capacity * 2);
        int hash = this.hash(key);
        int index = this.compress(hash);
        while (this.relations[index] != null) {
            hash = this.rehash(hash);
            index = this.compress(hash);
        }
        this.relations[index] = new Relation<>(key, value);
        ++this.size;
    }

    @Override
    public Value map(Key key) {
        int hash = this.hash(key);
        int index = this.compress(hash);
        while (this.relations[index] != null && this.relations[index] != Relation.SKIP) {
            hash = this.rehash(hash);
            index = this.compress(hash);
        }
        if (this.relations[index] == null) {
            return null;
        } else {
            return this.relations[index].getValue();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Value remove(Key key) {
        int hash = this.hash(key);
        int index = this.compress(hash);
        while (index >= 0 && this.relations[index] == Relation.SKIP) {
            hash = this.rehash(hash);
            index = this.compress(hash);
        }
        if (this.relations[index] == null) {
            return null;
        } else {
            Value value = this.relations[index].getValue();
            this.relations[index] = Relation.SKIP;
            --this.size;
            if (this.size < this.capacity / 8) this.resize(this.capacity / 2);
            return value;
        }
    }

    @Override
    public void enumerate(Consumer<Relation<Key, Value>> consumer) {
        for (Relation<Key, Value> relation : this.relations) {
            if (relation != null && relation != Relation.SKIP) {
                consumer.accept(relation);
            }
        }
    }

    protected int hash(Key key) {
        return key.hashCode();
    }

    protected int compress(int hash) {
        return hash % this.capacity;
    }

    protected abstract int rehash(int hash);

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Relation<Key, Value>[] old = this.relations;
        this.relations = new Relation[capacity];
        for (Relation<Key, Value> relation : old) {
            if (relation != null && relation != Relation.SKIP) {
                this.link(relation.getKey(), relation.getValue());
            }
        }
    }
}
