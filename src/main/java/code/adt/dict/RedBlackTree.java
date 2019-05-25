package code.adt.dict;

import java.util.function.Consumer;

public class RedBlackTree<Key extends Comparable<Key>, Value> implements BinarySearchTree<Key, Value> {
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

        private void traversal(Consumer<Key> consumer) {
            if (this.left != null) this.left.traversal(consumer);
            consumer.accept(this.key);
            if (this.right != null) this.right.traversal(consumer);
        }
    }

    private Node<Key, Value> root;

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    private Value get(Node<Key, Value> x, Key key) {
        while (x != null) {
            int diff = key.compareTo(x.key);
            if (diff < 0) x = x.left;
            else if (diff > 0) x = x.right;
            else return x.val;
        }
        return null;
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
    public void link(Key key, Value value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    @Override
    public Value map(Key key) {
        return get(root, key);
    }

    @Override
    public Value remove(Key key) {
        Value value = this.map(key);
        if (key != null) {
            if (!isRed(root.left) && !isRed(root.right))
                root.color = RED;

            root = delete(root, key);
            if (!this.isEmpty()) root.color = BLACK;
        }
        return value;
    }

    @Override
    public void enumerate(Consumer<Key> consumer) {
        if (this.root != null) this.root.traversal(consumer);
    }
}
