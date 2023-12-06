package collections.hashtable;

import java.util.function.Consumer;

public class OpenAddressingHashTable<Item>
        extends AbstractHashTable<Item> {
    private static final Object SKIP = new Object();

    private final Rehasher rehasher;
    private Object[] buckets;

    private OpenAddressingHashTable(final Rehasher rehasher) {
        this.rehasher = rehasher;
    }

    public static <Item> OpenAddressingHashTable<Item> linearProbeOpenAddressingHashMap() {
        return new OpenAddressingHashTable<>(Rehasher.LINEAR_PROBE_REHASHER);
    }

    public static <Item> OpenAddressingHashTable<Item> quadraticProbeOpenAddressingHashMap() {
        return new OpenAddressingHashTable<>(Rehasher.QUADRATIC_PROBE_REHASHER);
    }

    private static final Rehasher DOUBLE_HASH_REHASHER = new Rehasher() {
        private static final int Q = 7;

        @Override
        public int rehash(int hash, int key, int trial) {
            return hash + trial * (Q - (key % Q));
        }
    };

    public static <Item> OpenAddressingHashTable<Item> doubleHashProbeOpenAddressingHashMap() {
        return new OpenAddressingHashTable<>(DOUBLE_HASH_REHASHER);
    }

    @Override
    public void put(final Item item) {
        this.remove(item); // ensure that we can place at first SKIP position
        if (this.size > this.capacity * LOAD_FACTOR) this.resize(this.capacity * 2);
        int k = this.hash(item);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        for (var existingItem = this.buckets[index]; existingItem != null && existingItem != SKIP && !existingItem.equals(item); index = this.compress(this.rehasher.rehash(hash, k, i++)), existingItem = this.buckets[index])
            ;
        this.buckets[index] = item;
        ++this.size;
    }

    @Override
    public Item get(final Item hint) {
        int k = this.hash(hint);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        for (var existingItem = this.buckets[index]; existingItem != null && (existingItem == SKIP || !existingItem.equals(hint)); index = this.compress(this.rehasher.rehash(hash, k, i++)), existingItem = this.buckets[index])
            ;
        if (this.buckets[index] == null) {
            return null;
        } else {
            return (Item) this.buckets[index];
        }
    }

    @Override
    public void remove(final Item hint) {
        int k = this.hash(hint);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        for (var existingItem = this.buckets[index]; existingItem != null && (existingItem == SKIP || !existingItem.equals(hint)); index = this.compress(this.rehasher.rehash(hash, k, i++)), existingItem = this.buckets[index])
            ;
        if (this.buckets[index] != null) {
            this.buckets[index] = SKIP;
            --this.size;
            if (this.size < this.capacity * LOAD_FACTOR * LOAD_FACTOR * LOAD_FACTOR && this.capacity > DEFAULT_INITIAL_CAPACITY)
                this.resize(this.capacity / 2);
        }
    }

    @Override
    public void enumerate(Consumer<? super Item> consumer) {
        for (final var item : this.buckets) {
            if (item != null && item != SKIP) {
                consumer.accept((Item) item);
            }
        }
    }

    @Override
    protected void init(int capacity) {
        this.capacity = capacity;
        this.buckets = new Object[this.capacity];
        this.size = 0;
    }

    private void resize(int capacity) {
        var oldBuckets = this.buckets;
        this.init(capacity);
        for (var item : oldBuckets) {
            if (item != null && item != SKIP) {
                this.put((Item) item);
            }
        }
    }
}
