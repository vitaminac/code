package code.adt.dict;


import java.util.function.Consumer;

public class AVLTree<Key extends Comparable<Key>, Value> implements Dictionary<Key, Value> {
    private static class AVLNode<Key extends Comparable<Key>, Value> {
        private Key key;
        private Value value;
        private int height;
        private AVLNode<Key, Value> left;
        private AVLNode<Key, Value> right;

        private AVLNode(Key key, Value value, int height, AVLNode<Key, Value> left, AVLNode<Key, Value> right) {
            this.key = key;
            this.value = value;
            this.height = height;
            this.left = left;
            this.right = right;
        }

        private AVLNode(Key key, Value value) {
            this(key, value, 1, null, null);
        }

        private void traversal(Consumer<? super Key> consumer) {
            if (this.left != null) this.left.traversal(consumer);
            consumer.accept(this.key);
            if (this.right != null) this.right.traversal(consumer);
        }

        private int size() {
            return 1 + (this.left == null ? 0 : this.left.size()) + (this.right == null ? 0 : this.right.size());
        }

        private static <Key extends Comparable<Key>, Value> int height(AVLNode<Key, Value> node) {
            return node == null ? 0 : node.height;
        }

        private void setHeight() {
            this.height = Math.max(height(this.left), height(this.right)) + 1;
        }

        private int balance() {
            return height(this.left) - height(this.right);
        }

        private AVLNode<Key, Value> max() {
            if (this.right == null) {
                return this;
            } else {
                return this.right.max();
            }
        }
    }

    private AVLNode<Key, Value> root;

    @Override
    public int size() {
        if (this.isEmpty()) return 0;
        return this.root.size();
    }

    @Override
    public void enumerate(Consumer<? super Key> consumer) {
        this.root.traversal(consumer);
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    private AVLNode<Key, Value> restructure(AVLNode<Key, Value> node) {
        int balance = node.balance();

        // If this node becomes unbalanced, then there are 4 cases
        if (balance > 1) {
            if (node.left.balance() >= 0) {
                // Left Left Case
                return rightRotate(node);
            } else {
                // Left Right Case
                node.left = this.leftRotate(node.left);
                return this.rightRotate(node);
            }
        } else if (balance < -1) {
            if (node.right.balance() <= 0) {
                // Right Right Case
                return leftRotate(node);
            } else {
                // Right Left Case
                node.right = this.rightRotate(node.right);
                return leftRotate(node);
            }
        } else {
            /* return the (unchanged) node pointer */
            return node;
        }
    }

    private AVLNode<Key, Value> insert(AVLNode<Key, Value> node, Key key, Value value) {
        /* 1.  Perform the normal BST insertion */
        if (node == null) {
            return new AVLNode<>(key, value);
        }
        int diff = key.compareTo(node.key);
        if (diff == 0) {
            node.value = value;
        } else if (diff < 0) {
            node.left = this.insert(node.left, key, value);
        } else {
            node.right = this.insert(node.right, key, value);
        }

        /* 2. Update height of this ancestor node */
        node.setHeight();

        /* 3. Restructure T to fix any unbalance that may have occurred */
        return this.restructure(node);
    }

    private AVLNode<Key, Value> leftRotate(AVLNode<Key, Value> z) {
        AVLNode<Key, Value> y = z.right;
        z.right = y.left;
        z.setHeight();
        y.left = z;
        y.setHeight();
        return y;
    }

    private AVLNode<Key, Value> rightRotate(AVLNode<Key, Value> z) {
        AVLNode<Key, Value> y = z.left;
        z.left = y.right;
        z.setHeight();
        y.right = z;
        y.setHeight();
        return y;
    }

    @Override
    public void link(Key key, Value value) {
        this.root = this.insert(this.root, key, value);
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

    private AVLNode<Key, Value> delete(AVLNode<Key, Value> node, Key key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (node == null) {
            return null;
        } else {
            int diff = key.compareTo(node.key);
            if (diff == 0) {
                if (node.left == null) return node.right;
                else if (node.right == null) return node.left;
                else {
                    // Get the inorder predecessor
                    AVLNode<Key, Value> max = node.left.max();
                    // Copy the data to this node
                    node.key = max.key;
                    node.value = max.value;
                    // Delete the inorder predecessor
                    node.left = delete(node.left, max.key);
                }
            } else if (diff < 0) {
                // If the value to be deleted is smaller than the root's value,
                // then it lies in left subtree
                node.left = this.delete(node.left, key);
            } else {
                // If the value to be deleted is greater than the root's value,
                // then it lies in right subtree
                node.right = this.delete(node.right, key);
            }
            // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
            node.setHeight();

            // STEP 3: Restructure T to fix any unbalance that may have occurred
            return this.restructure(node);
        }
    }

    @Override
    public Value remove(Key key) {
        Value value = this.map(key);
        this.delete(this.root, key);
        return value;
    }

    @Override
    public void clear() {
        this.root = null;
    }

    public int getHeight() {
        return AVLNode.height(this.root);
    }
}