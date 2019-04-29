package code.adt.map;

public class HashTableMapQP<Key, Value> extends AbstractHashMap<Key, Value> {
    @Override
    protected int rehash(int hash) {
        return hash * hash;
    }
}
