package code.adt.dict;

import code.adt.LinkedList;
import code.adt.Position;
import code.adt.Queue;
import code.adt.Relation;
import code.adt.tree.LinkedBinaryTree;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class UnbalancedBinarySearchTree<Key extends Comparable<Key>, Value> implements BinarySearchTree<Key, Value> {
    protected LinkedBinaryTree<Relation<Key, Value>> binaryTree = new LinkedBinaryTree<>();

    protected LinkedBinaryTree.BTNode<Relation<Key, Value>> search(LinkedBinaryTree.BTNode<Relation<Key, Value>> position, Key key) {
        int diff = position.getElement().getKey().compareTo(key);
        if (diff == 0) {
            return position;
        } else if (diff < 0) {
            if (this.binaryTree.left(position) == null) {
                return position;
            } else {
                return this.search(this.binaryTree.left(position), key);
            }
        } else {
            if (this.binaryTree.right(position) == null) {
                return position;
            } else {
                return this.search(this.binaryTree.right(position), key);
            }
        }
    }

    protected Relation<Key, Value> createEntry(Key key, Value value) {
        return new Relation<>(key, value);
    }

    @Override
    public Iterable<Relation<Key, Value>> findRange(Key min, Key max) {
        Queue<Relation<Key, Value>> list = new LinkedList<>();
        this.enumerate((relation -> {
            if (relation.getKey().compareTo(min) >= 0 && relation.getKey().compareTo(max) <= 0) {
                list.enqueue(relation);
            }
        }));
        return list;
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
        Relation<Key, Value> entry = this.createEntry(key, value);
        if (this.isEmpty()) {
            this.binaryTree.root(entry);
        } else {
            LinkedBinaryTree.BTNode<Relation<Key, Value>> position = this.search(this.binaryTree.root(), key);
            int diff = position.getElement().getKey().compareTo(key);
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
        Position<Relation<Key, Value>> position = this.search(this.binaryTree.root(), key);
        if (position.getElement().getKey().compareTo(key) == 0) return position.getElement().getValue();
        return null;
    }

    @Override
    public Value remove(Key key) {
        if (this.isEmpty()) throw new NoSuchElementException();
        LinkedBinaryTree.BTNode<Relation<Key, Value>> position = this.search(this.binaryTree.root(), key);
        Value value = position.getElement().getValue();
        LinkedBinaryTree.BTNode<Relation<Key, Value>> parent = this.binaryTree.parent(position);
        if (position.getElement().getKey().compareTo(key) == 0) {
            if (this.binaryTree.isLeaf(position)) {
                this.binaryTree.remove(position);
            } else {
                if (this.binaryTree.hasLeft(position) && this.binaryTree.hasRight(position)) {
                    LinkedBinaryTree.BTNode<Relation<Key, Value>> rightMost = this.binaryTree.right(position);
                    while (this.binaryTree.hasRight(rightMost)) {
                        rightMost = this.binaryTree.right(rightMost);
                    }
                    this.remove(rightMost.getElement().getKey());
                    this.binaryTree.replace(position, rightMost.getElement());
                } else {
                    LinkedBinaryTree.BTNode<Relation<Key, Value>> child;
                    if (!this.binaryTree.hasLeft(position)) {
                        child = this.binaryTree.right(position);
                    } else {
                        child = this.binaryTree.left(position);
                    }
                    this.binaryTree.remove(position);
                    if (parent.getElement().getKey().compareTo(child.getElement().getKey()) < 0) {
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
    public void enumerate(Consumer<Relation<Key, Value>> consumer) {
        this.binaryTree.enumerate(position -> consumer.accept(position.getElement()));
    }
}
