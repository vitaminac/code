package code.adt.dict;

import code.adt.Position;
import code.adt.tree.LinkedBinaryTree;
import code.algorithm.divideandconquer.BinarySearch;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class UnbalancedBinarySearchTree<Key extends Comparable<Key>, Value> implements BinarySearchTree<Key, Value> {
    public static class Entry<Key extends Comparable<Key>, Value> implements Comparable<Entry<Key, Value>> {
        private Key key;
        private Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Entry<Key, Value> o) {
            return this.key.compareTo(o.key);
        }
    }

    private LinkedBinaryTree<Entry<Key, Value>> binaryTree = new LinkedBinaryTree<>();
    private BinarySearch<Entry<Key, Value>> binarySearch;

    public UnbalancedBinarySearchTree() {
        this.binarySearch = BinarySearch.get();
    }

    @Override
    public void findRange(Key min, Key max, Consumer<Key> consumer) {
        this.enumerate((key -> {
            if (key.compareTo(min) >= 0 && key.compareTo(max) <= 0) {
                consumer.accept(key);
            }
        }));
    }

    @Override
    public int size() {
        return this.binaryTree.size();
    }

    @Override
    public boolean isEmpty() {
        return this.binaryTree.isEmpty();
    }

    @Override
    public void link(Key key, Value value) {
        Entry<Key, Value> entry = new Entry<>(key, value);
        if (this.isEmpty()) {
            this.binaryTree.root(entry);
        } else {
            LinkedBinaryTree.BTNode<Entry<Key, Value>> position = this.binarySearch.search(this.binaryTree, entry);
            int diff = key.compareTo(position.getElement().key);
            if (diff == 0) {
                this.binaryTree.replace(position, entry);
            } else if (diff < 0) {
                this.binaryTree.left(position, entry);
            } else {
                this.binaryTree.right(position, entry);
            }
        }
    }

    @Override
    public Value map(Key key) {
        if (this.isEmpty()) return null;
        Position<Entry<Key, Value>> position = this.binarySearch.search(this.binaryTree, new Entry<>(key, null));
        if (position.getElement().key.compareTo(key) == 0) return position.getElement().value;
        return null;
    }

    @Override
    public Value remove(Key key) {
        if (this.isEmpty()) throw new NoSuchElementException();
        LinkedBinaryTree.BTNode<Entry<Key, Value>> position = this.binarySearch.search(this.binaryTree, new Entry<>(key, null));
        Value value = position.getElement().value;
        LinkedBinaryTree.BTNode<Entry<Key, Value>> parent = this.binaryTree.parent(position);
        if (key.compareTo(position.getElement().key) == 0) {
            if (this.binaryTree.isLeaf(position)) {
                this.binaryTree.remove(position);
            } else {
                if (this.binaryTree.hasLeft(position) && this.binaryTree.hasRight(position)) {
                    LinkedBinaryTree.BTNode<Entry<Key, Value>> rightMost = this.binaryTree.right(position);
                    while (this.binaryTree.hasRight(rightMost)) {
                        rightMost = this.binaryTree.right(rightMost);
                    }
                    this.remove(rightMost.getElement().key);
                    this.binaryTree.replace(position, rightMost.getElement());
                } else {
                    LinkedBinaryTree.BTNode<Entry<Key, Value>> child;
                    if (!this.binaryTree.hasLeft(position)) {
                        child = this.binaryTree.right(position);
                    } else {
                        child = this.binaryTree.left(position);
                    }
                    this.binaryTree.remove(position);
                    if (child.getElement().key.compareTo(parent.getElement().key) < 0) {
                        this.binaryTree.linkLeft(parent, child);
                    } else {
                        this.binaryTree.linkRight(parent, child);
                    }
                }
            }
            return value;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void enumerate(Consumer<Key> consumer) {
        this.binaryTree.enumerate(position -> consumer.accept(position.getElement().key));
    }
}
