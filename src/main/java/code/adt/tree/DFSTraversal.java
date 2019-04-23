package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

import java.util.function.Consumer;

public class DFSTraversal<E, P extends Position<E>, T extends NAryTree<E, P>> implements Enumerable<P> {
    private final T tree;

    public DFSTraversal(T tree) {
        this.tree = tree;
    }

    @Override
    public void enumerate(Consumer<P> consumer) {
        this.traversal(this.tree.root(), consumer);
    }

    public void traversal(P node, Consumer<P> consumer) {
        if (node == null) return;
        consumer.accept(node);
        this.tree.children(node).enumerate(p -> traversal(p, consumer));
    }
}
