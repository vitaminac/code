package code.adt.dict;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

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
        });
    }


    @Before
    public void setUp() {
        this.dictionary = supplier.get();
    }

    @Test
    public void findRange() {

    }
}