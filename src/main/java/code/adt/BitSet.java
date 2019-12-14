package code.adt;

import code.adt.set.Set;

import java.util.function.Consumer;

public class BitSet implements Set<Integer> {
    private int size = 0;
    private boolean[] set;

    public BitSet(int n) {
        this.set = new boolean[n];
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(Integer n) {
        return this.set[n];
    }

    @Override
    public void add(Integer n) {
        if (!this.set[n]) {
            this.size++;
            this.set[n] = true;
        }
    }

    @Override
    public void remove(Integer n) {
        if (this.set[n]) {
            this.size--;
            this.set[n] = false;
        }
    }

    public BitSet intersect(BitSet set) {
        int len = Math.min(this.set.length, set.set.length);
        BitSet r = new BitSet(len);
        for (int i = 0; i < len; i++) {
            if (this.contains(i) && set.contains(i)) r.add(i);
        }
        return r;
    }

    public BitSet union(BitSet set) {
        int len = Math.min(this.set.length, set.set.length);
        BitSet r = new BitSet(len);
        for (int i = 0; i < len; i++) {
            if (this.contains(i) || set.contains(i)) r.add(i);
        }
        return r;
    }

    public BitSet difference(BitSet set) {
        int len = Math.min(this.set.length, set.set.length);
        BitSet r = new BitSet(len);
        for (int i = 0; i < len; i++) {
            if (this.contains(i) && !set.contains(i)) r.add(i);
        }
        return r;
    }

    public BitSet symmetricDifference(BitSet set) {
        return this.difference(set).union(set.difference(this));
    }

    public boolean isSubset(BitSet set) {
        return set.all(this::contains);
    }

    public boolean isSuperSet(BitSet set) {
        return set.isSubset(this);
    }

    public boolean isDisjointFrom(BitSet set) {
        return set.all(n -> !this.contains(n));
    }

    @Override
    public void clear() {
        this.set = new boolean[this.set.length];
        this.size = 0;
    }

    @Override
    public void enumerate(Consumer<Integer> consumer) {
        for (int i = 0; i < this.set.length; i++) {
            if (this.set[i]) {
                consumer.accept(i);
            }
        }
    }
}
