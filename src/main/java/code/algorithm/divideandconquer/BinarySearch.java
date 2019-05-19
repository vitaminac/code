package code.algorithm.divideandconquer;

import code.adt.ArrayList;
import code.adt.List;
import code.adt.Position;
import code.adt.tree.BinaryTree;
import greedy.graph.Tree;

import java.util.Comparator;

public class BinarySearch<E> {
    private final Comparator<? super E> comparator;

    public BinarySearch(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public int binarySearchRecursive(E[] array, E key) {
        return binarySearchRecursive(array, key, 0, array.length - 1);
    }

    private int binarySearchRecursive(E[] array, E key, int start, int end) {
        int mid = (start + end) / 2;
        E mean = array[mid];
        if (start > end) {
            return -1;
        } else {
            int dff = this.comparator.compare(key, mean);
            if (dff == 0) {
                return mid;
            } else if (dff > 0) {
                return binarySearchRecursive(array, key, mid + 1, end);
            } else {
                return binarySearchRecursive(array, key, start, mid - 1);
            }
        }
    }

    public int binarySearchIterative(E[] array, E key) {
        return this.binarySearchIterative(new ArrayList<>(array), key);
    }

    public int binarySearchIterative(List<E> list, E key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int diff = this.comparator.compare(list.get(mid), key);
            if (diff < 0) low = mid + 1;
            else if (diff > 0) high = mid - 1;
            else return mid; // key found
        }
        return low;  // key not found
    }

    private <P extends Position<E>> P search(P position, BinaryTree<E, P> tree, E key) {
        int diff = this.comparator.compare(key, position.getElement());
        if (diff == 0) {
            return position;
        } else if (diff < 0) {
            if (tree.left(position) == null) {
                return position;
            } else {
                return this.search(tree.left(position), tree, key);
            }
        } else {
            if (tree.right(position) == null) {
                return position;
            } else {
                return this.search(tree.right(position), tree, key);
            }
        }
    }

    public <P extends Position<E>> P search(BinaryTree<E, P> tree, E key) {
        return this.search(tree.root(), tree, key);
    }

    public static <T extends Comparable<? super T>> BinarySearch<T> get() {
        return new BinarySearch<>(T::compareTo);
    }
}
