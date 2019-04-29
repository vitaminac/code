package code.adt.map;

public class HashTableMapDH<Key, Value> extends AbstractHashMap<Key, Value> {
    private static final int PRIME_M = 31;
    private static final int PRIME_B = 1610612741;

    @Override
    protected int rehash(int hash) {
        return hash * PRIME_M + PRIME_B;
    }
}
