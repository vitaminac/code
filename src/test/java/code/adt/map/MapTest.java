package code.adt.map;

import code.adt.dict.AVLTree;
import code.adt.dict.UnbalancedBinarySearchTree;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MapTest {
    private Map<String, String> map;
    private Supplier<Map<String, String>> supplier;

    public MapTest(Supplier<Map<String, String>> supplier) {
        this.supplier = supplier;
    }

    @Before
    public void setUp() throws Exception {
        this.map = this.supplier.get();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][]{
                {(Supplier<Map<String, String>>) HashTableMapDH::new},
                {(Supplier<Map<String, String>>) HashTableMapLP::new},
                {(Supplier<Map<String, String>>) HashTableMapQP::new},
                {(Supplier<Map<String, String>>) () -> new HashTableMapSC<>(256)},
                {(Supplier<Map<String, String>>) UnbalancedBinarySearchTree::new},
               // {(Supplier<Map<String, String>>) AVLTree::new},
        });
    }

    @Test
    public void size() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, this.map.size());
            this.map.link(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(1000, this.map.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(this.map.isEmpty());
        this.map.link("0", "0");
        assertFalse(this.map.isEmpty());
    }

    @Test
    public void link() {
        for (int i = 0; i < 1000; i++) {
            this.map.link(String.valueOf(i), String.valueOf(i));
        }
        this.map.link("0", "hello");
        this.map.link("1", "world");
        this.map.link("2", "today");
        this.map.link("3", "afternoon");
        this.map.link("4", "night");
        this.map.link("5", "morning");

        assertEquals("105", this.map.map("105"));
        assertEquals("hello", this.map.map("0"));
        assertEquals("world", this.map.map("1"));
        assertEquals("today", this.map.map("2"));
        assertEquals("afternoon", this.map.map("3"));
        assertEquals("night", this.map.map("4"));
        assertEquals("morning", this.map.map("5"));
    }

    @Test
    public void map() {
        for (int i = 0; i < 1000; i++) {
            this.map.link(String.valueOf(i), "N:" + String.valueOf(i));
        }
        assertEquals("N:100", this.map.map("100"));
    }

    @Test
    public void remove() {
        for (int i = 0; i < 1000; i++) {
            this.map.link(String.valueOf(i), String.valueOf(i));
        }
        this.map.link("0", "hello");
        this.map.link("1", "world");
        this.map.link("2", "today");
        this.map.link("3", "afternoon");
        this.map.link("4", "night");
        this.map.link("5", "morning");

        this.map.remove("100");
        this.map.remove("55");
        assertEquals("105", this.map.map("105"));
        assertNull(this.map.map("100"));
        assertNull(this.map.map("55"));
    }

    @Test
    public void addAfterRemove() {
        for (int i = 0; i < 1000; i++) {
            this.map.link(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(1000, this.map.size());
        for (int i = 300; i < 400; i++) {
            assertEquals(1000 - (i - 300), this.map.size());
            this.map.remove(String.valueOf(i));
        }
        assertEquals(900, this.map.size());
        this.map.link("350", "Correct");
        assertEquals("Correct", this.map.map("350"));
    }
}