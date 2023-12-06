package collections.tree;

import core.functional.Enumerable;
import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.queue.Queue;

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
            this.children().forEach(child -> child.preOrder().forEach(consumer));
        };
    }

    @Override
    default Enumerable<SelfType> postOrder() {
        return consumer -> {
            this.children().forEach(child -> child.preOrder().forEach(consumer));
            consumer.accept((SelfType) this);
        };
    }

    default Enumerable<SelfType> bfs() {
        return consumer -> {
            Queue<SelfType> unvisited = Queue.fromSteque(SinglyLinkedListDoubleReference::new);
            unvisited.enqueue((SelfType) this);
            while (!unvisited.isEmpty()) {
                var node = unvisited.dequeue();
                consumer.accept(node);
                this.children().forEach(unvisited::enqueue);
            }
        };
    }
}
