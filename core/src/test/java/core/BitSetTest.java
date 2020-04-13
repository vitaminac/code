package core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BitSetTest {
    private BitSet set;

    @Before
    public void setUp() throws Exception {
        this.set = new BitSet(100);
    }

    @Test
    public void isEmpty() {
        assertTrue(this.set.isEmpty());
        this.set.add(76);
        assertFalse(this.set.isEmpty());
        this.set.remove(76);
        assertTrue(this.set.isEmpty());
    }

    @Test
    public void size() {
        assertEquals(0, this.set.size());
        this.set.add(76);
        assertEquals(1, this.set.size());
        this.set.add(76);
        assertEquals(1, this.set.size());
        this.set.remove(99);
        assertEquals(1, this.set.size());
        this.set.add(78);
        assertEquals(2, this.set.size());
        this.set.remove(76);
        assertEquals(1, this.set.size());
        this.set.remove(78);
        assertEquals(0, this.set.size());
    }

    @Test
    public void contains() {
        assertFalse(this.set.contains(76));
        this.set.add(76);
        assertTrue(this.set.contains(76));
        this.set.remove(76);
        assertFalse(this.set.contains(76));
    }

    @Test
    public void add() {
        this.set.add(66);
        assertTrue(this.set.contains(66));
    }

    @Test
    public void remove() {
        assertTrue(this.set.isEmpty());
        this.set.add(0);
        assertFalse(this.set.isEmpty());
        this.set.remove(0);
        assertTrue(this.set.isEmpty());
        assertTrue(this.set.isEmpty());
        this.set.add(99);
        assertFalse(this.set.isEmpty());
        this.set.remove(99);
        assertTrue(this.set.isEmpty());
    }

    @Test
    public void intersect() {
        BitSet set1 = new BitSet(100);
        BitSet set2 = new BitSet(100);
        set1.add(18);
        set1.add(39);
        set1.add(76);
        set2.add(14);
        set2.add(39);
        set2.add(75);
        this.set = set1.intersect(set2);
        assertEquals(1, this.set.size());
        assertFalse(this.set.contains(18));
        assertTrue(this.set.contains(39));
        assertFalse(this.set.contains(76));
        assertFalse(this.set.contains(14));
        assertFalse(this.set.contains(75));
    }

    @Test
    public void union() {
        BitSet set1 = new BitSet(100);
        BitSet set2 = new BitSet(100);
        set1.add(18);
        set1.add(39);
        set1.add(76);
        set2.add(14);
        set2.add(39);
        set2.add(75);
        this.set = set1.union(set2);
        assertEquals(5, this.set.size());
        assertTrue(this.set.contains(18));
        assertTrue(this.set.contains(39));
        assertTrue(this.set.contains(76));
        assertTrue(this.set.contains(14));
        assertTrue(this.set.contains(75));
    }

    @Test
    public void difference() {
        BitSet set1 = new BitSet(100);
        BitSet set2 = new BitSet(100);
        set1.add(18);
        set1.add(39);
        set1.add(76);
        set2.add(14);
        set2.add(39);
        set2.add(75);
        this.set = set1.difference(set2);
        assertEquals(2, this.set.size());
        assertTrue(this.set.contains(18));
        assertFalse(this.set.contains(39));
        assertTrue(this.set.contains(76));
    }

    @Test
    public void symmetricDifference() {
        BitSet set1 = new BitSet(100);
        BitSet set2 = new BitSet(100);
        set1.add(18);
        set1.add(39);
        set1.add(76);
        set2.add(14);
        set2.add(39);
        set2.add(75);
        set2.add(99);
        this.set = set1.symmetricDifference(set2);
        assertEquals(5, this.set.size());
        assertTrue(this.set.contains(18));
        assertFalse(this.set.contains(39));
        assertTrue(this.set.contains(76));
        assertTrue(this.set.contains(14));
        assertTrue(this.set.contains(75));
        assertTrue(this.set.contains(99));
    }

    @Test
    public void isSubset() {
        BitSet set = new BitSet(100);
        assertTrue(this.set.isSubset(set));
        assertTrue(set.isSubset(this.set));
        set.add(5);
        assertFalse(this.set.isSubset(set));
        assertTrue(set.isSubset(this.set));
        this.set.add(5);
        assertTrue(this.set.isSubset(set));
        assertTrue(set.isSubset(this.set));
        this.set.add(6);
        assertFalse(set.isSubset(this.set));
        assertTrue(this.set.isSubset(set));
    }

    @Test
    public void isSuperSet() {
        BitSet set = new BitSet(100);
        assertTrue(this.set.isSuperSet(set));
        assertTrue(set.isSuperSet(this.set));
        set.add(5);
        assertTrue(this.set.isSuperSet(set));
        assertFalse(set.isSuperSet(this.set));
        this.set.add(5);
        assertTrue(this.set.isSuperSet(set));
        assertTrue(set.isSuperSet(this.set));
        this.set.add(6);
        assertTrue(set.isSuperSet(this.set));
        assertFalse(this.set.isSuperSet(set));
    }

    @Test
    public void isDisjointFrom() {
        BitSet set1 = new BitSet(100);
        BitSet set2 = new BitSet(100);
        set1.add(5);
        set1.add(6);
        set2.add(7);
        set2.add(8);
        assertTrue(set1.isDisjointFrom(set2));
        set2.add(5);
        assertFalse(set1.isDisjointFrom(set2));
    }

    @Test
    public void clear() {
        assertEquals(0, this.set.size());
        this.set.add(88);
        assertEquals(1, this.set.size());
        this.set.clear();
        assertEquals(0, this.set.size());
    }

    @Test
    public void enumerate() {
    }
}