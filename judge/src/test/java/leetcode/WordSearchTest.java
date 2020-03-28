package leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class WordSearchTest {
    private WordSearch wordSearch = new WordSearch();

    @Test
    public void exist() {
        assertFalse(wordSearch.exist(new char[][]{{'a', 'a', 'a', 'a'}, {'a', 'a', 'a', 'a'}, {'a', 'a', 'a', 'a'}}, "aaaaaaaaaaaaa"));
    }
}