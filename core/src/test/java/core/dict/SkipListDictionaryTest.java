package core.dict;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class SkipListDictionaryTest {

    private SkipListDictionary<Integer, String> dictionary;


    @Before
    public void setUp() {
        this.dictionary = new SkipListDictionary<>();
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