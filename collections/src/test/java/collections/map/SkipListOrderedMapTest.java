package collections.map;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class SkipListOrderedMapTest {
    private SkipListOrderedMap<Integer, String> skiplist;

    @Before
    public void setUp() {
        this.skiplist = new SkipListOrderedMap<>();
    }

    @Test
    public void firstKey() {
        this.skiplist.put(2, "2");
        this.skiplist.put(5, "2");
        this.skiplist.put(3, "2");
        this.skiplist.put(1, "2");
        this.skiplist.put(0, "2");
        this.skiplist.put(-1, "2");
        assertEquals(-1, (int) this.skiplist.firstKey());
    }

    @Test
    public void findRange() {
        for (int i = 0; i < 1000; i++) {
            this.skiplist.put(i, String.valueOf(i));
        }
        final StringBuilder sb = new StringBuilder();
        this.skiplist.findRange((int) 'A', (int) 'z', new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                sb.append((char) (int) integer);
            }
        });
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz", sb.toString());
    }
}