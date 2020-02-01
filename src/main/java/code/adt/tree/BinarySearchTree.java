package code.adt.tree;

public interface BinarySearchTree<E extends Comparable<? super E>, SelfType extends BinarySearchTree<E, SelfType>> extends BinaryTree<E, SelfType> {
    default SelfType search(E key) {
        int diff = key.compareTo(this.getElement());
        if (diff == 0) return (SelfType) this;
        else if (diff < 0) {
            if (this.left() == null) return (SelfType) this;
            else return this.left().search(key);
        } else {
            if (this.right() == null) return (SelfType) this;
            else return this.right().search(key);
        }
    }
}
