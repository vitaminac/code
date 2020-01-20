package code.adt.tree;

import code.adt.Enumerable;
import code.adt.LinkedList;
import code.adt.Position;
import code.adt.Queue;

import java.util.function.Consumer;

public interface NAryTree<E> extends Tree<E> {
    Position<E> get(String path);

    // TODO: fix, remove name and change method name to addChild
    Position<E> add(Position<E> position, String name, E element);

    Enumerable<Position<E>> children(Position<E> position);

    private void preOrderTraversal(Position<E> node, Consumer<Position<E>> consumer) {
        if (node == null) return;
        consumer.accept(node);
        this.children(node).enumerate(consumer);
    }

    @Override
    default Enumerable<Position<E>> preOrder() {
        return consumer -> this.preOrderTraversal(this.root(), consumer);
    }

    private void postOrderTraversal(Position<E> node, Consumer<Position<E>> consumer) {
        if (node == null) return;
        this.children(node).enumerate(consumer);
        consumer.accept(node);
    }

    @Override
    default Enumerable<Position<E>> postOrder() {
        return consumer -> this.postOrderTraversal(this.root(), consumer);
    }

    default Enumerable<Position<E>> bfs() {
        return consumer -> {
            Queue<Position<E>> unvisited = new LinkedList<>();
            unvisited.enqueue(this.root());
            while (!unvisited.isEmpty()) {
                var node = unvisited.dequeue();
                consumer.accept(node);
                this.children(node).enumerate(unvisited::enqueue);
            }
        };
    }

    default Enumerable<Position<E>> dfs() {
        return this.preOrder();
    }

    @Override
    default void enumerate(Consumer<Position<E>> consumer) {
        this.dfs().enumerate(consumer);
    }
}
