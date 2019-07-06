package code.adt.map;

import code.adt.ArrayList;
import code.adt.List;

import java.util.function.Consumer;

public class HashTableMapSC<Key, Value> implements Map<Key, Value> {
    private List<Relation<Key, Value>> relations[];
    private int size;

    public HashTableMapSC(int capacity) {
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
    public void link(Key key, Value value) {
        int index = this.hash(key);
        if (this.relations[index] == null) {
            this.relations[index] = new ArrayList<>(new Relation<>(key, value));
            ++this.size;
        } else {
            Relation<Key, Value> newRelation = new Relation<>(key, value);
            for (int i = 0; i < this.relations[index].size(); i++) {
                Relation<Key, Value> relation = this.relations[index].get(i);
                if (relation.getKey().equals(key)) {
                    this.relations[index].set(i, newRelation);
                    return;
                }
            }
            this.relations[index].add(0, newRelation);
            ++this.size;
        }
    }

    @Override
    public Value map(Key key) {
        int index = this.hash(key);
        if (this.relations[index] != null) {
            for (Relation<Key, Value> relation : this.relations[index]) {
                if (relation.getKey().equals(key)) {
                    return relation.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Value remove(Key key) {
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
    public void clear() {
        this.init(this.relations.length);
    }

    @Override
    public void enumerate(Consumer<Key> consumer) {
        for (List<Relation<Key, Value>> relations : this.relations) {
            for (Relation<Key, Value> relation : relations) {
                consumer.accept(relation.getKey());
            }
        }
    }

    private int hash(Key key) {
        return key.hashCode() % this.relations.length;
    }

    @SuppressWarnings("unchecked")
    private void init(int capacity) {
        this.relations = new ArrayList[capacity];
        this.size = 0;
    }
}
