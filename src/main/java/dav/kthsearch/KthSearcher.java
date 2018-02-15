package dav.kthsearch;

import java.util.List;

public class KthSearcher<E extends Comparable<? super E>> {
    public int select(List<E> list, int start, int end, int k) {
        Pair<Integer, Integer> p = this.partition(list, start, end);
        int left = p.getLeft();
        int right = p.getRight();
        if ((left - start + 1) > k) {
            return this.select(list, start, --left, k);
        } else if (right + 1 < k) {
            return right + this.select(list, right + 1, end, k - (right - start + 1)) + 1;
        } else {
            return k - 1;
        }
    }

    public int searchKSmallest(List<E> list, int k) {
        return this.select(list, 0, list.size() - 1, k);
    }

    public E median(List<E> list) {
        int n = list.size();
        if (n % 2 == 0) {
            n = n / 2;
        } else {
            n = n / 2 + 1;
        }
        return list.get(this.searchKSmallest(list, n));
    }

    private void swap(List<E> list, int left, int right) {
        E tmp = list.get(left);
        list.set(left, list.get(right));
        list.set(right, tmp);

    }

    private Pair<Integer, Integer> partition(List<E> list, int start, int end) {
        int left = start;
        int right = left;
        E pivot = list.get(left);
        while (end > right) {
            int diff = pivot.compareTo(list.get(right + 1));
            if (diff < 0) {
                this.swap(list, right + 1, end--);
            } else if (diff > 0) {
                this.swap(list, left++, ++right);
            } else {
                right++;
            }
        }
        return new Pair<>(left, right);
    }

    private class Pair<L, R> {
        private final L left;
        private final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return this.left;
        }

        public R getRight() {
            return this.right;
        }
    }
}
