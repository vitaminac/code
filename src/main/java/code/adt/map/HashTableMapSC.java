package code.adt.map;

import code.adt.ArrayList;
import code.adt.List;
import code.adt.Relation;

import java.util.function.Consumer;

public class HashTableMapSC<K, V> implements Map<K, V> {
    private List<Relation<K, V>> relations[];

    private int size = 0;

    @SuppressWarnings("unchecked")
    public HashTableMapSC(int capacity) {
        this.relations = new ArrayList[capacity];
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
    public void link(K key, V value) {
        int index = this.hash(key);
        if (this.relations[index] == null) {
            this.relations[index] = new ArrayList<>(new Relation<>(key, value));
            ++this.size;
        } else {
            for (Relation<K, V> relation : this.relations[index]) {
                if (relation.getKey().equals(key)) {
                    break;
                }
            }
            this.relations[index].add(0, new Relation<>(key, value));
            ++this.size;
        }
    }

    @Override
    public V map(K key) {
        int index = this.hash(key);
        if (this.relations[index] != null) {
            for (Relation<K, V> relation : this.relations[index]) {
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
        int index = this.hash(key);
        if (this.relations[index] != null) {
            for (int i = 0; i < this.relations[index].size(); i++) {
                if (this.relations[index].get(i).getKey().equals(key)) {
                    found = i;
                    break;
                }
            }
        }
        if (found < 0) {
            return null;
        } else {
            --this.size;
            return this.relations[index].remove(found).getValue();
        }
    }

    @Override
    public void enumerate(Consumer<Relation<K, V>> consumer) {
        for (List<Relation<K, V>> relations : this.relations) {
            for (Relation<K, V> relation : relations) {
                consumer.accept(relation);
            }
        }
    }

    private int hash(K key) {
        return key.hashCode() % this.relations.length;
    }
}
