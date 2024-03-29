package collections.hashtable;

public abstract class AbstractHashTable<Item>
        implements HashTable<Item> {
    protected static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    protected static final double LOAD_FACTOR = 0.5;
    protected int capacity;
    protected int size;

    public AbstractHashTable() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public AbstractHashTable(int capacity) {
        this.init(capacity);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        this.init(DEFAULT_INITIAL_CAPACITY);
    }

    protected int hash(final Item item) {
        return item.hashCode();
    }

    protected int compress(int hash) {
        return Math.floorMod(hash, this.capacity);
    }

    protected abstract void init(int capacity);
}
