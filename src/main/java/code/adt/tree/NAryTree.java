package code.adt.tree;

import code.adt.Enumerable;
import code.adt.LinkedList;
import code.adt.Queue;

public interface NAryTree<E, SelfType extends NAryTree<E, SelfType>> extends Tree<E, SelfType> {
    void addChild(SelfType tree);

    Enumerable<SelfType> children();

    @Override
    default int height() {
        return this.children().map(NAryTree::height).reduce(0, Math::max) + 1;
    }

    @Override
    default int size() {
        return this.children().map(NAryTree::size).reduce(0, Integer::sum) + 1;
    }

    @Override
    default Enumerable<SelfType> preOrder() {
        return consumer -> {
            consumer.accept((SelfType) this);
            this.children().enumerate(child -> child.preOrder().enumerate(consumer));
        };
    }

    @Override
    default Enumerable<SelfType> postOrder() {
        return consumer -> {
            this.children().enumerate(child -> child.preOrder().enumerate(consumer));
            consumer.accept((SelfType) this);
        };
    }

    default Enumerable<SelfType> bfs() {
        return consumer -> {
            Queue<SelfType> unvisited = new LinkedList<>();
            unvisited.enqueue((SelfType) this);
            while (!unvisited.isEmpty()) {
                var node = unvisited.dequeue();
                consumer.accept(node);
                this.children().enumerate(unvisited::enqueue);
            }
        };
    }
}
