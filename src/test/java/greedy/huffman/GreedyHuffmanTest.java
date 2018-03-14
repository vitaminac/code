package greedy.huffman;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class GreedyHuffmanTest {
    private Constructor<?> nodeBuilder;
    private Method countsCharacters;

    @Before
    public void setUp() throws Exception {
        this.nodeBuilder = Class.forName("greedy.huffman.GreedyHuffman$Tree").getDeclaredConstructor(char.class, int.class);
        this.countsCharacters = GreedyHuffman.class.getDeclaredMethod("countsCharacters", String.class);
        this.countsCharacters.setAccessible(true);
    }

    @Test
    public void testCountsCharacters() throws Exception {
        String testStr = "Huffman coding is a lossless data compression algorithm.";

        final HashSet o = new HashSet((Collection) countsCharacters.invoke(null, testStr));
        HashSet set = new HashSet();
        set.add(this.nodeBuilder.newInstance('H', 1));
        set.add(this.nodeBuilder.newInstance('u', 1));
        set.add(this.nodeBuilder.newInstance('f', 2));
        set.add(this.nodeBuilder.newInstance('m', 3));
        set.add(this.nodeBuilder.newInstance('a', 5));
        set.add(this.nodeBuilder.newInstance('n', 3));
        set.add(this.nodeBuilder.newInstance(' ', 7));
        set.add(this.nodeBuilder.newInstance('c', 2));
        set.add(this.nodeBuilder.newInstance('o', 5));
        set.add(this.nodeBuilder.newInstance('d', 2));
        set.add(this.nodeBuilder.newInstance('i', 4));
        set.add(this.nodeBuilder.newInstance('g', 2));
        set.add(this.nodeBuilder.newInstance('s', 7));
        set.add(this.nodeBuilder.newInstance('l', 3));
        set.add(this.nodeBuilder.newInstance('e', 2));
        set.add(this.nodeBuilder.newInstance('p', 1));
        set.add(this.nodeBuilder.newInstance('r', 2));
        set.add(this.nodeBuilder.newInstance('t', 2));
        set.add(this.nodeBuilder.newInstance('h', 1));
        set.add(this.nodeBuilder.newInstance('.', 1));
        assertEquals(set, o);
    }

    @Test
    public void testCodingMap() {
        String testStr = "abcaba";
        final GreedyHuffman greedyHuffman = new GreedyHuffman(testStr);
        HashMap<Character, String> mapping = new HashMap<>();
        mapping.put('a', "0");
        mapping.put('b', "11");
        mapping.put('c', "10");
        assertEquals(mapping, greedyHuffman.createCodingMap(testStr));
    }
}