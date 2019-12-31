package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

public interface NAryTree<E> extends Tree<E> {
    Position<E> get(String path);

    Position<E> add(Position<E> position, String name, E element);

    Enumerable<Position<E>> children(Position<E> position);
}
