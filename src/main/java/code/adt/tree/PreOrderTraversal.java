package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

import java.util.function.Consumer;

public class PreOrderTraversal<E, P extends Position<E>> implements Enumerable<P> {
    private BinaryTree<E, P> tree;

    public PreOrderTraversal(BinaryTree<E, P> tree) {
        this.tree = tree;
    }

    @Override
    public void enumerate(Consumer<P> consumer) {
        this.traversal(this.tree.root(), consumer);
    }

    public void traversal(P node, Consumer<P> consumer) {
        if (node == null) return;
        consumer.accept(node);
        traversal(this.tree.left(node), consumer);
        traversal(this.tree.right(node), consumer);
    }
}
