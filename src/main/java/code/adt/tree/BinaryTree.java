package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

import java.util.function.Consumer;

public interface BinaryTree<E> extends Tree<E> {

    Position<E> left(Position<E> position);

    Position<E> right(Position<E> position);

    boolean hasLeft(Position<E> position);

    boolean hasRight(Position<E> position);

    Position<E> sibling(Position<E> p);

    Position<E> left(Position<E> position, E element);

    Position<E> right(Position<E> position, E element);

    @Override
    default boolean isInternal(Position<E> position) {
        return this.hasLeft(position) || this.hasRight(position);
    }

    @Override
    default boolean isLeaf(Position<E> position) {
        return !this.isInternal(position);
    }

    private void preOrderTraversal(Position<E> node, Consumer<? super Position<E>> consumer) {
        if (node == null) return;
        consumer.accept(node);
        this.preOrderTraversal(this.left(node), consumer);
        this.preOrderTraversal(this.right(node), consumer);
    }

    @Override
    default Enumerable<Position<E>> preOrder() {
        return consumer -> this.preOrderTraversal(this.root(), consumer);
    }

    private void postOrderTraversal(Position<E> node, Consumer<? super Position<E>> consumer) {
        if (node == null) return;
        this.postOrderTraversal(this.left(node), consumer);
        this.postOrderTraversal(this.right(node), consumer);
        consumer.accept(node);
    }

    @Override
    default Enumerable<Position<E>> postOrder() {
        return consumer -> this.postOrderTraversal(this.root(), consumer);
    }

    private void inOrderTraversal(Position<E> node, Consumer<? super Position<E>> consumer) {
        if (node == null) return;
        this.inOrderTraversal(this.left(node), consumer);
        consumer.accept(node);
        this.inOrderTraversal(this.right(node), consumer);
    }

    @Override
    default void enumerate(Consumer<? super Position<E>> consumer) {
        this.inOrderTraversal(this.root(), consumer);
    }

    default Enumerable<Position<E>> inOrder() {
        return this;
    }
}
