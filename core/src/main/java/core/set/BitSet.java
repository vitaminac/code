package core.set;

import java.util.function.Consumer;

import core.util.Math;

public class BitSet implements MutableSet<Integer> {
    private boolean[] set;
    private int size;

    public BitSet(int n) {
        this.set = new boolean[n];
    }

    @Override
    public boolean contains(Integer n) {
        return this.set[n];
    }

    @Override
    public void add(Integer n) {
        if (!this.set[n]) {
            this.set[n] = true;
            this.size += 1;
        }
    }

    @Override
    public void remove(Integer n) {
        if (this.set[n]) {
            this.set[n] = false;
            this.size -= 1;
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

    public boolean isSubset(BitSet set) {
        for (int i = 0; i < this.set.length; i++) {
            if (!this.set[i] && set.set[i]) return false;
        }
        for (int i = this.set.length; i < set.set.length; i++) {
            if (set.set[i]) return false;
        }
        return true;
    }

    public boolean isSuperSet(BitSet set) {
        return set.isSubset(this);
    }

    public boolean isDisjointFrom(BitSet set) {
        for (int i = 0; i < this.set.length; i++) {
            if (this.set[i] && set.contains(i))
                return false;
        }
        return true;
    }

    public void clear() {
        this.set = new boolean[this.set.length];
        this.size = 0;
    }

    @Override
    public void enumerate(Consumer<? super Integer> consumer) {
        for (int i = 0; i < this.set.length; i++) if (this.set[i]) consumer.accept(i);
    }

    @Override
    public int size() {
        return this.size;
    }
}
