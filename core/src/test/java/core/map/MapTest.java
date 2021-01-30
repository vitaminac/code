package core.map;

import core.dict.SkipListDictionary;
import core.tree.AVLTree;
import core.tree.RedBlackTree;
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
    private final Supplier<Map<String, String>> supplier;

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
                {(Supplier<Map<String, String>>) OpenAddressingHashTableMapDH::new},
                {(Supplier<Map<String, String>>) OpenAddressingHashTableMapLP::new},
                {(Supplier<Map<String, String>>) OpenAddressingHashTableMapQP::new},
                {(Supplier<Map<String, String>>) SeparateChainingHashTableMap::new},
                {(Supplier<Map<String, String>>) () -> new TreeMap<>(AVLTree::new)},
                {(Supplier<Map<String, String>>) () -> new TreeMap<>(RedBlackTree::new)},
                {(Supplier<Map<String, String>>) SkipListDictionary::new},
        });
    }

    @Test
    public void sizeInc() {
        for (int i = 0; i < 10; i++) {
            assertEquals(i, this.map.size());
            this.map.put(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(10, this.map.size());
    }

    @Test
    public void sizeDec() {
        for (int i = 0; i < 10; i++) {
            assertEquals(i, this.map.size());
            this.map.put(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(10, this.map.size());
        for (int i = 9; i >= 0; i--) {
            this.map.remove(String.valueOf(i));
            assertEquals(i, this.map.size());
        }
        assertEquals(0, this.map.size());
    }

    @Test
    public void size() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, this.map.size());
            this.map.put(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(1000, this.map.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(this.map.isEmpty());
        this.map.put("0", "0");
        assertFalse(this.map.isEmpty());
    }

    @Test
    public void link() {
        for (int i = 0; i < 1000; i++) {
            this.map.put(String.valueOf(i), String.valueOf(i));
            assertEquals(String.valueOf(i), this.map.get(String.valueOf(i)));
        }
        assertEquals("105", this.map.get("105"));

        this.map.put("0", "hello");
        assertEquals("hello", this.map.get("0"));

        this.map.put("1", "world");
        assertEquals("world", this.map.get("1"));

        this.map.put("2", "today");
        assertEquals("today", this.map.get("2"));

        this.map.put("3", "afternoon");
        assertEquals("afternoon", this.map.get("3"));

        this.map.put("4", "night");
        assertEquals("night", this.map.get("4"));

        this.map.put("5", "morning");
        assertEquals("morning", this.map.get("5"));
    }

    @Test
    public void map() {
        for (int i = 0; i < 1000; i++) {
            this.map.put(String.valueOf(i), "N:" + i);
            assertEquals("N:" + i, this.map.get(String.valueOf(i)));
        }
    }

    @Test
    public void testAddOneRemoveOne() {
        this.map.put("0", "hello");
        assertEquals("hello", this.map.get("0"));
        assertEquals(1, this.map.size());
        this.map.remove("0");
        assertEquals(0, this.map.size());
    }

    @Test
    public void remove() {
        for (int i = 0; i < 1000; i++) {
            this.map.put(String.valueOf(i), String.valueOf(i));
        }
        this.map.put("0", "hello");
        this.map.put("1", "world");
        this.map.put("2", "today");
        this.map.put("3", "afternoon");
        this.map.put("4", "night");
        this.map.put("5", "morning");

        this.map.remove("100");
        this.map.remove("55");
        assertEquals("105", this.map.get("105"));
        assertNull(this.map.get("100"));
        assertNull(this.map.get("55"));
    }

    @Test
    public void addAfterRemove() {
        for (int i = 0; i < 1000; i++) {
            this.map.put(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(1000, this.map.size());
        for (int i = 300; i < 400; i++) {
            assertEquals(1000 - (i - 300), this.map.size());
            this.map.remove(String.valueOf(i));
        }
        assertEquals(900, this.map.size());
        this.map.put("350", "Correct");
        assertEquals("Correct", this.map.get("350"));
    }

    @Test
    public void clear() {
        for (int i = 0; i < 1000; i++) {
            this.map.put(String.valueOf(i), String.valueOf(i));
        }
        assertEquals(1000, this.map.size());
        for (int i = 0; i < 1000; i++) {
            assertNotNull(this.map.get(String.valueOf(i)));
        }
        this.map.clear();
        assertEquals(0, this.map.size());
        for (int i = 0; i < 1000; i++) {
            assertNull(this.map.get(String.valueOf(i)));
        }
    }
}