package code.adt.dict;


import code.adt.tree.LinkedBinaryTree;

import java.util.function.Consumer;

public class AVLTree<Key extends Comparable<Key>, Value> implements BinarySearchTree<Key, Value> {
    private static class AVLNode<Key extends Comparable<Key>, Value> {
        private Key key;
        private Value value;
        private int height;
        private AVLNode<Key, Value> parent;
        private AVLNode<Key, Value> left;
        private AVLNode<Key, Value> right;

        public AVLNode(Key key, Value value, int height, AVLNode<Key, Value> parent, AVLNode<Key, Value> left, AVLNode<Key, Value> right) {
            this.key = key;
            this.value = value;
            this.height = height;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public AVLNode(Key key, Value value) {
            this(key, value, 1, null, null, null);
        }
    }

    private AVLNode<Key, Value> root;
    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void findRange(Key min, Key max, Consumer<Key> consumer) {
        this.enumerate(key -> {
            if (key.compareTo(min) >= 0 && key.compareTo(max) <= 0) {
                consumer.accept(key);
            }
        });
    }

    private void traversal(Consumer<Key> consumer, AVLNode<Key, Value> node) {
        if (node != null) {
            this.traversal(consumer, node.left);
            consumer.accept(node.key);
            this.traversal(consumer, node.right);
        }
    }

    @Override
    public void enumerate(Consumer<Key> consumer) {
        this.traversal(consumer, this.root);
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public void link(Key key, Value value) {
        AVLNode<Key, Value> node = new AVLNode<>(key, value);
        if (this.isEmpty()) {
            this.root = node;
            this.size = 1;
        } else {
            AVLNode<Key, Value> current = this.root;
            int diff = key.compareTo(current.key);
            while (diff != 0) {
                if (diff < 0) {
                    if (current.left == null) {
                        this.left(current, node);
                        this.reBalance(node);
                        this.size += 1;
                        return;
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null) {
                        this.right(current, node);

                        this.reBalance(node);
                        this.size += 1;
                        return;
                    } else {
                        current = current.right;
                    }
                }
                diff = key.compareTo(current.key);
            }
            current.value = value;
        }
    }

    @Override
    public Value map(Key key) {
        AVLNode<Key, Value> current = this.root;
        while (current != null) {
            int diff = key.compareTo(current.key);
            if (diff == 0) {
                return current.value;
            } else if (diff < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    @Override
    public Value remove(Key key) {
        return null;
    }

    public int getHeight() {
        return this.root == null ? 0 : this.root.height;
    }

    private void linkLeft(AVLNode<Key, Value> node, AVLNode<Key, Value> left) {
        node.left = left;
        if (left != null) {
            left.parent = node;
        }
    }

    private void linkRight(AVLNode<Key, Value> node, AVLNode<Key, Value> right) {
        node.right = right;
        if (right != null) {
            right.parent = node;
        }
    }

    private void left(AVLNode<Key, Value> node, AVLNode<Key, Value> left) {
        node.left = left;
        left.parent = node;
    }

    private void right(AVLNode<Key, Value> node, AVLNode<Key, Value> right) {
        node.right = right;
        right.parent = node;
    }

    private AVLNode<Key, Value> left(AVLNode<Key, Value> node) {
        return node.left;
    }

    private AVLNode<Key, Value> right(AVLNode<Key, Value> node) {
        return node.right;
    }

    private AVLNode<Key, Value> parent(AVLNode<Key, Value> node) {
        return node.parent;
    }

    private void root(AVLNode<Key, Value> node) {
        this.root = node;
        node.parent = null;
    }

    private void assignHeight(AVLNode<Key, Value> node) {
        node.height = 1 + Math.max(this.height(this.left(node)), this.height(this.right(node)));
    }

    private int height(AVLNode<Key, Value> node) {
        return node == null ? 0 : node.height;
    }

    private boolean isBalanced(AVLNode<Key, Value> node) {
        return Math.abs(this.height(this.left(node)) - this.height(this.right(node))) <= 1;
    }

    private void rotate(AVLNode<Key, Value> x) {
        AVLNode<Key, Value> y = this.parent(x);
        AVLNode<Key, Value> z = this.parent(y);
        if (this.left(y) == x) {
            this.linkLeft(y, this.right(x));
            this.linkRight(x, y);
        } else {
            this.linkRight(y, this.left(x));
            this.linkLeft(x, y);
        }
        this.assignHeight(y);
        this.assignHeight(x);
        if (z == null) {
            this.root(x);
        } else {
            if (this.left(z) == y) {
                this.linkLeft(z, x);
            } else {
                this.linkRight(z, x);
            }
            this.assignHeight(z);
        }
    }

    private void reBalance(AVLNode<Key, Value> node) {
        this.assignHeight(node);
        AVLNode<Key, Value> parent = this.parent(node);
        if (parent != null) {
            this.assignHeight(parent);
            AVLNode<Key, Value> grandparent = this.parent(parent);
            while (grandparent != null) {
                if (!this.isBalanced(grandparent)) {
                    if ((this.left(grandparent) == parent) == (this.left(parent) == node)) {
                        this.rotate(parent);
                    } else {
                        this.rotate(node);
                        this.rotate(node);
                    }
                }
                this.assignHeight(grandparent);
                node = parent;
                parent = grandparent;
                grandparent = this.parent(grandparent);
            }
        }
    }
}