package core.dict;

import java.util.function.Consumer;

/**
 * https://algs4.cs.princeton.edu/33balanced/
 *
 * @param <Key>
 * @param <Value>
 */
public class RedBlackTree<Key extends Comparable<Key>, Value> implements Dictionary<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node<Key, Value> {
        private Key key;
        private Value val;
        private Node<Key, Value> left, right;
        private boolean color;
        private int size;

        private Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }

        private void traversal(Consumer<Node<Key, Value>> consumer) {
            if (this.left != null) this.left.traversal(consumer);
            consumer.accept(this);
            if (this.right != null) this.right.traversal(consumer);
        }
    }

    private Node<Key, Value> root;

    private boolean isRed(Node x) {
        // All Leaves are black
        return x != null && x.color == RED;
    }

    private int size(Node x) {
        return x == null ? 0 : x.size;
    }

    private Node<Key, Value> rotateRight(Node<Key, Value> z) {
        Node<Key, Value> y = z.left;
        z.left = y.right;
        y.right = z;
        y.color = z.color;
        z.color = RED;
        y.size = z.size;
        z.size = size(z.left) + size(z.right) + 1;
        return y;
    }

    private Node<Key, Value> rotateLeft(Node<Key, Value> z) {
        Node<Key, Value> y = z.right;
        z.right = y.left;
        y.left = z;
        y.color = z.color;
        z.color = RED;
        y.size = z.size;
        z.size = size(z.left) + size(z.right) + 1;
        return y;
    }

    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node<Key, Value> put(Node<Key, Value> z, Key key, Value val) {
        /*
        Insert into a 2-node.
        To insert a new node in a 2-3 tree,
        we might do an unsuccessful search and then hook on the node at the bottom,
        as we did with BSTs,
        but the new tree would not remain perfectly balanced.
        It is easy to maintain perfect balance
        if the node at which the search terminates is a 2-node:
        We just replace the node with a 3-node containing its key
        and the new key to be inserted.
         */
        if (z == null) return new Node<>(key, val, RED, 1);

        int diff = key.compareTo(z.key);
        if (diff < 0) z.left = put(z.left, key, val);
        else if (diff > 0) z.right = put(z.right, key, val);
        else z.val = val;

        if (isRed(z.right) && !isRed(z.left)) z = rotateLeft(z);
        if (isRed(z.left) && isRed(z.left.left)) z = rotateRight(z);
        if (isRed(z.left) && isRed(z.right)) flipColors(z);
        z.size = size(z.left) + size(z.right) + 1;

        return z;
    }

    private Node<Key, Value> moveRedLeft(Node<Key, Value> h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private Node<Key, Value> moveRedRight(Node<Key, Value> h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private Node<Key, Value> balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    private Node<Key, Value> min(Node<Key, Value> x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    private Node<Key, Value> deleteMin(Node<Key, Value> h) {
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node<Key, Value> delete(Node<Key, Value> h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node<Key, Value> x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
        root.color = BLACK; // The root is black
    }

    @Override
    public Value get(Key key) {
        var node = this.root;
        while (node != null) {
            int diff = key.compareTo(node.key);
            if (diff < 0) node = node.left;
            else if (diff > 0) node = node.right;
            else return node.val;
        }
        return null;
    }

    @Override
    public Value remove(Key key) {
        Value value = this.get(key);
        if (key != null) {
            if (!isRed(root.left) && !isRed(root.right))
                root.color = RED;

            root = delete(root, key);
            if (!this.isEmpty()) root.color = BLACK;
        }
        return value;
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public void forEach(Consumer<? super Key> consumer) {
        if (this.root != null) this.root.traversal(node -> consumer.accept(node.key));
    }

    private void buildTree(StringBuilder sb, Node<Key, Value> node, int level) {
        sb.append(System.lineSeparator());
        if (!this.isRed(node)) level += 1;
        if (node != null) this.buildTree(sb, node.right, level);
        for (int i = 0; i < level; i++) {
            sb.append('\t');
        }
        sb.append(node == null ? "*" : (this.isRed(node) ? "\u001B[31m" + node.key : String.valueOf(node.key)));
        if (node != null) this.buildTree(sb, node.left, level);
        sb.append(System.lineSeparator());
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        this.buildTree(sb, this.root, -1);
        return sb.toString();
    }
}
