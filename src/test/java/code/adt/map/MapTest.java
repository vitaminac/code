package code.adt.map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

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
                {(Supplier<Map<String, String>>) () -> new HashTableMapSC<>(256)}
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
    }

    @Test
    public void link() {
    }

    @Test
    public void map() {
    }

    @Test
    public void remove() {
    }
}