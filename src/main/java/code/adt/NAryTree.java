package code.adt;

import java.util.Iterator;

public interface NAryTree<E, P extends Position<E>> {
    P get(String path);

    E remove(P position);

    Iterator<P> children(P position);

    P add(P position, String name, E element);

    P root();

    P parent(P position);

    boolean isInternal(P position);

    boolean isLeaf(P position);
}
