package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;
import code.adt.Queue;

import java.util.function.Consumer;

public class BFSTraversal<E> implements Enumerable<Position<E>> {
    private final NAryTree<E> tree;
    private Queue<Position<E>> queue;

    public BFSTraversal(NAryTree<E> tree) {
        this.tree = tree;
    }

    @Override
    public void enumerate(Consumer<Position<E>> consumer) {
        this.traversal(this.tree.root(), consumer);
    }

    public void traversal(Position<E> node, Consumer<Position<E>> consumer) {
        if (node == null) return;
        consumer.accept(node);
        this.tree.children(node).enumerate(p -> {
            queue.enqueue(p);
        });
        if (!queue.isEmpty()) this.traversal(queue.dequeue(), consumer);
    }
}
