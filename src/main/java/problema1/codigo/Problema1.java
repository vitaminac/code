package problema1.codigo;

import java.util.Comparator;


public class Problema1<E extends Comparable<E>> {

    Comparator<E> comp;

    public Problema1(Comparator<E> c) {
        this.comp = c;
    }

    private void sort(E[] arr, E[] tmp, int left, int right) {
        int mid = (left + right) / 2;
        if (mid > left) {
            this.sort(arr, tmp, left, mid);
            this.sort(arr, tmp, mid, right);
            this.merge(arr, tmp, left, mid, right);
        }
    }

    private void merge(E[] arr, E[] tmp, int left, int mid, int right) {
        for (int idx = left; idx < right; idx++) {
            tmp[idx] = arr[idx];
        }
        for (int idx = left, i = left, j = mid; idx < right; idx++) {
            if (i >= mid) {
                arr[idx] = tmp[j++];
            } else if (j >= right) {
                arr[idx] = tmp[i++];
            } else if (this.comp.compare(tmp[i], tmp[j]) < 0) {
                arr[idx] = tmp[i++];
            } else {
                arr[idx] = tmp[j++];
            }
        }
    }

    public void sort(E[] a) {
        E tmp[] = (E[]) new Comparable[a.length];
        this.sort(a, tmp, 0, a.length);
    }
}
