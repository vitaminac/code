package greedy.huffman;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Stack;

public class GreedyHuffman {
    private static class Tree implements Comparable<Tree> {
        private Tree left = null;
        private Tree right = null;
        private char character = '\0';
        private int frequency;

        Tree(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        Tree(Tree left, Tree right) {
            this.left = left;
            this.right = right;
            this.frequency = left.frequency + right.frequency;
        }

        @Override
        public int compareTo(Tree o) {
            return this.frequency - o.frequency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Tree))
                return false;
            Tree tree = (Tree) o;
            return character == tree.character && frequency == tree.frequency && Objects.equals(left, tree.left) && Objects.equals(right, tree.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right, character, frequency);
        }
    }

    private HashMap<Character, String> codingMap;

    GreedyHuffman(String str) {
        this.codingMap = createCodingMap(str);
    }

    public HashMap<Character, String> createCodingMap(String string) {
        PriorityQueue<Tree> frequencies = countsCharacters(string);
        Tree huffmanTree = buildHuffmanTree(frequencies);
        return createCodingMap(huffmanTree);
    }

    private static HashMap<Character, String> createCodingMap(Tree currentTree) {
        HashMap<Character, String> mapping = new HashMap<>();
        StringBuilder codingBuilder = new StringBuilder();
        Stack<Tree> stack = new Stack<>();
        while (!stack.isEmpty() || currentTree != null) {
            while (currentTree != null) {
                stack.push(currentTree);
                currentTree = currentTree.left;
                codingBuilder.append('0');
            }
            currentTree = stack.pop();
            codingBuilder.deleteCharAt(codingBuilder.length() - 1);
            if (currentTree != null) {
                if (currentTree.right == null) {
                    mapping.put(currentTree.character, codingBuilder.toString());
                }
                currentTree = currentTree.right;
                stack.push(null);
                codingBuilder.append('1');
            }
        }
        return mapping;
    }

    private static Tree buildHuffmanTree(PriorityQueue<Tree> charsFrequencies) {
        while (charsFrequencies.size() > 1) {
            Tree left = charsFrequencies.poll();
            Tree right = charsFrequencies.poll();
            charsFrequencies.add(new Tree(left, right));
        }
        return charsFrequencies.poll();
    }

    private static PriorityQueue<Tree> countsCharacters(String string) {
        HashMap<Character, Integer> characters = new HashMap<>();
        for (char c : string.toCharArray()) {
            if (characters.containsKey(c)) {
                characters.put(c, characters.get(c) + 1);
            } else {
                characters.put(c, 1);
            }
        }
        PriorityQueue<Tree> huffmanTree = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> node : characters.entrySet()) {
            huffmanTree.add(new Tree(node.getKey(), node.getValue()));
        }
        return huffmanTree;
    }
}
