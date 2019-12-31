package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

import java.util.function.Consumer;

public class PreOrderTraversal<E> implements Enumerable<Position<E>> {
    private BinaryTree<E> tree;

    public PreOrderTraversal(BinaryTree<E> tree) {
        this.tree = tree;
    }

    @Override
    public void enumerate(Consumer<Position<E>> consumer) {
        this.traversal(this.tree.root(), consumer);
    }

    public void traversal(Position<E> node, Consumer<Position<E>> consumer) {
        if (node == null) return;
        consumer.accept(node);
        traversal(this.tree.left(node), consumer);
        traversal(this.tree.right(node), consumer);
    }
}
