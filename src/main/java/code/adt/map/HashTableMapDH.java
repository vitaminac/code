package code.adt.map;

public class HashTableMapDH<Key, Value> extends AbstractHashMap<Key, Value> {
    private static final int Q = 7;

    @Override
    protected int rehash(int hash, int key, int trial) {
        return hash + trial * (Q - (key % Q));
    }
}
