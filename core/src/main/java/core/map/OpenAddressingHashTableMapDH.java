package core.map;

public class OpenAddressingHashTableMapDH<Key, Value> extends AbstractOpenAddressingHashMap<Key, Value> {
    private static final int Q = 7;

    @Override
    protected int rehash(int hash, int key, int trial) {
        return hash + trial * (Q - (key % Q));
    }
}
