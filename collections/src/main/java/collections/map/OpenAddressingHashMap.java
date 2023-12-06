package collections.map;

import java.util.function.Consumer;

public class OpenAddressingHashMap<Key, Value> extends AbstractHashMap<Key, Value, Relation<Key, Value>> {
    private static final Relation SKIP = new Relation<>(null, null);

    private final Rehasher rehasher;

    private OpenAddressingHashMap(final Rehasher rehasher) {
        this.rehasher = rehasher;
    }

    public static <Key, Value> OpenAddressingHashMap<Key, Value> linearProbeOpenAddressingHashMap() {
        return new OpenAddressingHashMap<>(Rehasher.LINEAR_PROBE_REHASHER);
    }

    public static <Key, Value> OpenAddressingHashMap<Key, Value> quadraticProbeOpenAddressingHashMap() {
        return new OpenAddressingHashMap<>(Rehasher.QUADRATIC_PROBE_REHASHER);
    }

    private static final Rehasher DOUBLE_HASH_REHASHER = new Rehasher() {
        private static final int Q = 7;

        @Override
        public int rehash(int hash, int key, int trial) {
            return hash + trial * (Q - (key % Q));
        }
    };

    public static <Key, Value> OpenAddressingHashMap<Key, Value> doubleHashProbeOpenAddressingHashMap() {
        return new OpenAddressingHashMap<>(DOUBLE_HASH_REHASHER);
    }

    @Override
    public void put(Key key, Value value) {
        this.remove(key); // ensure that we can place at first SKIP position
        if (this.size > this.capacity * LOAD_FACTOR) this.resize(this.capacity * 2);
        int k = this.hash(key);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        for (Relation<Key, Value> relation = this.entries[index]; relation != null && relation != SKIP && !relation.getKey().equals(key); index = this.compress(this.rehasher.rehash(hash, k, i++)), relation = this.entries[index])
            ;
        this.entries[index] = new Relation<>(key, value);
        ++this.size;
    }

    @Override
    public Value get(Key key) {
        int k = this.hash(key);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        for (Relation<Key, Value> relation = this.entries[index]; relation != null && (relation == SKIP || !relation.getKey().equals(key)); index = this.compress(this.rehasher.rehash(hash, k, i++)), relation = this.entries[index])
            ;
        if (this.entries[index] == null) {
            return null;
        } else {
            return this.entries[index].getValue();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void remove(Key key) {
        int k = this.hash(key);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        for (Relation<Key, Value> relation = this.entries[index]; relation != null && (relation == SKIP || !relation.getKey().equals(key)); index = this.compress(this.rehasher.rehash(hash, k, i++)), relation = this.entries[index])
            ;
        if (this.entries[index] != null) {
            this.entries[index] = SKIP;
            --this.size;
            if (this.size < this.capacity * LOAD_FACTOR * LOAD_FACTOR * LOAD_FACTOR && this.capacity > DEFAULT_INITIAL_CAPACITY)
                this.resize(this.capacity / 2);
        }
    }

    @Override
    public void enumerate(Consumer<? super Key> consumer) {
        for (Relation<Key, Value> relation : this.entries) {
            if (relation != null && relation != SKIP) {
                consumer.accept(relation.getKey());
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init(int capacity) {
        this.capacity = capacity;
        this.entries = new Relation[this.capacity];
        this.size = 0;
    }

    private void resize(int capacity) {
        var old = this.entries;
        this.init(capacity);
        for (var relation : old) {
            if (relation != null && relation != SKIP) {
                this.put(relation.getKey(), relation.getValue());
            }
        }
    }
}
