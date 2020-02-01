package code.adt.tree;

import code.adt.Position;

public interface BinarySearchTree<E extends Comparable<? super E>> extends BinaryTree<E> {
    private Position<E> search(Position<E> position, E key) {
        int diff = key.compareTo(position.getElement());
        if (diff == 0) {
            return position;
        } else if (diff < 0) {
            if (this.left(position) == null) {
                return position;
            } else {
                return this.search(this.left(position), key);
            }
        } else {
            if (this.right(position) == null) {
                return position;
            } else {
                return this.search(this.right(position), key);
            }
        }
    }

    default Position<E> search(E key) {
        return this.search(this.root(), key);
    }
}
