package code.adt.tree.iterators;

import code.adt.Position;
import code.adt.tree.Tree;

import java.util.Iterator;
import java.util.function.Predicate;


public class PreorderIterator<E> implements Iterator<Position<E>> {


    public PreorderIterator(Tree<E> tree, Position<E> start) {
        throw new RuntimeException("Not yet implemented");
    }

    public PreorderIterator(Tree<E> tree, Position<E> start, Predicate<E> predicate) {
        throw new RuntimeException("Not yet implemented");
    }

    public PreorderIterator(Tree<E> tree) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean hasNext() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Position<E> next() {
        throw new RuntimeException("Not yet implemented");
    }

}
