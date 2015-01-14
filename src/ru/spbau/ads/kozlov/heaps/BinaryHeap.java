package ru.spbau.ads.kozlov.heaps;

import java.util.Collection;

/**
 * @author adkozlov
 */
public class BinaryHeap<T extends Comparable<T>> extends AbstractHeap<T> {

    public BinaryHeap(int capacity) {
        super(capacity);
    }

    public BinaryHeap(Collection<T> values) {
        super(values);

        build();
    }

    @Override
    protected void siftUp(int v) {
        for (int w = v / 2; get(v).compareTo(get(w)) < 0; v = w, w /= 2) {
            swap(v, w);
        }
    }

    @Override
    protected void siftDown(int v) {
        while (2 * v < size()) {
            int left = 2 * v;
            int right = 2 * v + 1;

            int j = left;
            if (right < size() && get(right).compareTo(get(left)) < 0) {
                j = right;
            }
            if (get(v).compareTo(get(j)) <= 0) {
                break;
            }

            swap(v, j);
            v = j;
        }
    }

    @Override
    public void build() {
        for (int v = size() / 2; v >= 0; v--) {
            siftDown(v);
        }
    }
}
