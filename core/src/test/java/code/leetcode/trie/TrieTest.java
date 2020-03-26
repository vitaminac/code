package code.leetcode.trie;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrieTest {
    @Test
    public void test() {
        Trie trie = new Trie();

        trie.insert("apple");
        assertTrue(trie.search("apple"));
        assertFalse(trie.search("app"));
        assertTrue(trie.startsWith("app"));
        trie.insert("app");
        assertTrue(trie.search("app"));
    }
}