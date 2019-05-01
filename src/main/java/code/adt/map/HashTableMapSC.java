package code.adt.map;

import code.adt.ArrayList;

import java.util.function.Consumer;

public class HashTableMapSC<K, V> implements Map<K, V> {
    private Map<K, ArrayList<Relation<K, V>>> map = new AbstractHashMap<K, ArrayList<Relation<K, V>>>() {
        @Override
        protected int rehash(int hash) {
            throw new RuntimeException();
        }
    };

    private int size = 0;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void link(K key, V value) {
        if (this.map.map(key) == null || this.map.map(key).isEmpty()) {
            this.map.link(key, new ArrayList<>(new Relation<>(key, value)));
            ++this.size;
        } else {
            for (Relation<K, V> relation : this.map.map(key)) {
                if (relation.getKey().equals(key)) {
                    break;
                }
            }
            this.map.map(key).add(new Relation<>(key, value));
            ++this.size;
        }
    }

    @Override
    public V map(K key) {
        if (this.map.map(key) != null) {
            for (Relation<K, V> relation : this.map.map(key)) {
                if (relation.getKey().equals(key)) {
                    return relation.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int found = -1;
        if (this.map.map(key) != null) {
            for (int i = 0; i < this.map.map(key).size(); i++) {
                if (this.map.map(key).get(i).getKey().equals(key)) {
                    found = i;
                    break;
                }
            }
        }
        if (found < 0) {
            return null;
        } else {
            return this.map.map(key).remove(found).getValue();
        }
    }

    @Override
    public void enumerate(Consumer<K> consumer) {
        this.map.enumerate(k -> HashTableMapSC.this.map.map(k).enumerate(relation -> consumer.accept(relation.getKey())));
    }
}
