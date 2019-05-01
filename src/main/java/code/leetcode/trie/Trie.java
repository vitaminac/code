package code.leetcode.trie;

/**
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 **/
public class Trie {
    private static class TrieNode {
        TrieNode[] next = new TrieNode[26];
        boolean end;
    }

    private TrieNode root = new TrieNode();

    /**
     * Initialize your data structure here.
     */
    public Trie() {
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            TrieNode next;
            next = current.next[c - 'a'];
            if (next == null) {
                next = new TrieNode();
                current.next[c - 'a'] = next;
            }
            current = next;
        }
        current.end = true;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.next[c - 'a'];
            if (current == null) {
                return false;
            }
        }
        return current.end;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            current = current.next[c - 'a'];
            if (current == null) {
                return false;
            }
        }
        return true;
    }
}
/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */