package greedy.huffman;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class GreedyHuffman {
    /*
     * arbol de frecuencia
     * */
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
    }

    /**
     * devuelva la correspondiente codificación Huffman
     */
    public static GreedyHuffman createCodingMap(String string) {
        PriorityQueue<Tree> frequencies = countsCharacters(string);
        Tree huffmanTree = buildHuffmanTree(frequencies);
        return new GreedyHuffman(createCodingMap(huffmanTree));
    }

    public static GreedyHuffman createCodingMap(File text) throws FileNotFoundException {
        return createCodingMap(readContent(text));
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

    /**
     * construya el árbol de codificación para el vocabulario correspondiente
     */
    private static Tree buildHuffmanTree(PriorityQueue<Tree> charsFrequencies) {
        while (charsFrequencies.size() > 1) {
            Tree left = charsFrequencies.poll();
            Tree right = charsFrequencies.poll();
            charsFrequencies.add(new Tree(left, right));
        }
        return charsFrequencies.poll();
    }

    /**
     * calcule las frecuencias de ocurrencias de cada carácter de un string
     */
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

    /**
     * carga los contenidos de un texto a un string
     */
    private static String readContent(File file) throws FileNotFoundException {
        StringBuilder fileContents = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine());
            }
            while (scanner.hasNextLine()) {
                fileContents.append(lineSeparator);
                fileContents.append(scanner.nextLine());
            }
        }
        return fileContents.toString();
    }

    public static void main(String[] args) {
        try {
            final GreedyHuffman codingMap = createCodingMap(new File("texto.txt"));
            System.out.println(codingMap.toString());
            System.out.println("codificacion Huffman de la palabra 'de':" + codingMap.mapWord("de"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final HashMap<Character, String> codingMap;

    private GreedyHuffman(HashMap<Character, String> codingMap) {
        this.codingMap = codingMap;
    }

    public String mapWord(String word) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            sb.append(this.codingMap.get(c));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "GreedyHuffman{" + "codingMap=" + codingMap + '}';
    }
}
