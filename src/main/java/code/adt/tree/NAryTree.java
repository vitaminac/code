package code.adt.tree;

import code.adt.Enumerable;
import code.adt.Position;

public interface NAryTree<E, P extends Position<E>> extends Tree<E, P> {
    P get(String path);

    P add(P position, String name, E element);

    Enumerable<P> children(P position);
}
