package core.tree;

import core.Enumerable;

public interface BinarySearchTree<E extends Comparable<? super E>, SelfType extends BinarySearchTree<E, SelfType>> extends Tree<E, SelfType> {
    default SelfType search(E key) {
        int diff = key.compareTo(this.getElement());
        if (diff == 0) return (SelfType) this;
        else if (diff < 0) {
            if (this.left() == null) return (SelfType) this;
            else return this.left().search(key);
        } else {
            if (this.right() == null) return (SelfType) this;
            else return this.right().search(key);
        }
    }

    SelfType left();

    SelfType right();

    @Override
    default int height() {
        return Math.max(this.left() == null ? 0 : this.left().height(), this.right() == null ? 0 : this.right().height()) + 1;
    }

    @Override
    default int size() {
        int size = 1;
        if (this.left() != null) size += this.left().size();
        if (this.right() != null) size += this.right().size();
        return size;
    }

    @Override
    default boolean isLeaf() {
        return this.left() == null && this.right() == null;
    }

    @Override
    default Enumerable<SelfType> preOrder() {
        return consumer -> {
            consumer.accept((SelfType) this);
            if (this.left() != null) this.left().preOrder().forEach(consumer);
            if (this.right() != null) this.right().preOrder().forEach(consumer);
        };
    }

    @Override
    default Enumerable<SelfType> postOrder() {
        return consumer -> {
            if (this.left() != null) this.left().postOrder().forEach(consumer);
            if (this.right() != null) this.right().postOrder().forEach(consumer);
            consumer.accept((SelfType) this);
        };
    }

    default Enumerable<SelfType> inOrder() {
        return consumer -> {
            if (this.left() != null) this.left().inOrder().forEach(consumer);
            consumer.accept((SelfType) this);
            if (this.right() != null) this.right().inOrder().forEach(consumer);
        };
    }
}
