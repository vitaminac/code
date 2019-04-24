package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;
import code.adt.Queue;

import java.util.function.Consumer;

public class BFSTraversal<E, P extends Position<E>, T extends NAryTree<E, P>> implements Enumerable<P> {
    private final T tree;
    private Queue<P> queue;

    public BFSTraversal(T tree) {
        this.tree = tree;
    }

    @Override
    public void enumerate(Consumer<P> consumer) {
        this.traversal(this.tree.root(), consumer);
    }

    public void traversal(P node, Consumer<P> consumer) {
        if (node == null) return;
        consumer.accept(node);
        this.tree.children(node).enumerate(p -> {
            queue.enqueue(p);
        });
        if (!queue.isEmpty()) this.traversal(queue.dequeue(), consumer);
    }
}
