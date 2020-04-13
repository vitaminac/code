package core.map;

public class OpenAddressingHashTableMapLP<Key, Value> extends AbstractOpenAddressingHashMap<Key, Value> {
    @Override
    protected int rehash(int hash, int key, int trial) {
        return hash + trial;
    }
}
