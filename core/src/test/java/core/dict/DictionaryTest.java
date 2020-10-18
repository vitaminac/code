package core.dict;

import core.map.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DictionaryTest {

    private Dictionary<Integer, String> dictionary;
    private Supplier<Dictionary<Integer, String>> supplier;

    public DictionaryTest(Supplier<Dictionary<Integer, String>> supplier) {
        this.supplier = supplier;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][]{
                {(Supplier<Dictionary<Integer, String>>) AVLTree::new},
                {(Supplier<Map<Integer, String>>) RedBlackTree::new},
                {(Supplier<Dictionary<Integer, String>>) SkipListMap::new},
        });
    }


    @Before
    public void setUp() {
        this.dictionary = supplier.get();
    }

    @Test
    public void findRange() {
        for (int i = 0; i < 1000; i++) {
            this.dictionary.put(i, String.valueOf(i));
        }
        final StringBuilder sb = new StringBuilder();
        this.dictionary.findRange((int) 'A', (int) 'z', new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                sb.append((char) (int) integer);
            }
        });
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz", sb.toString());
    }
}