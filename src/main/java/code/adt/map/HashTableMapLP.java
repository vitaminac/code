package code.adt.map;

public class HashTableMapLP<Key, Value> extends AbstractHashMap<Key, Value> {
    @Override
    protected int rehash(int hash, int key, int trial) {
        return hash + trial;
    }
}