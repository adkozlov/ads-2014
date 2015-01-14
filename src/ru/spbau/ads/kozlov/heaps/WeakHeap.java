package ru.spbau.ads.kozlov.heaps;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author adkozlov
 */
public class WeakHeap<T extends Comparable<T>> extends AbstractHeap<T> {

    private final int[] bits;

    public WeakHeap(int capacity) {
        super(capacity);

        bits = new int[capacity];
    }

    public WeakHeap(Collection<T> values) {
        super(values);

        bits = new int[values.size()];
        build();
    }

    private boolean join(int v, int w) {
        if (get(v).compareTo(get(w)) < 0) {
            swap(v, w);
            bits[v] ^= 1;

            return true;
        }

        return false;
    }

    private int up(int v) {
        if (v == 0) {
            return 0;
        }

        while ((v & 1) == bits[v / 2]) {
            v /= 2;
        }

        return v / 2;
    }

    @Override
    protected void siftUp(int v) {
        for (int w = up(v); join(v, w); w = up(v)) {
            v = w;
        }
    }

    @Override
    protected void siftDown(int v) {
        for (int w = 1; w < size(); w = 2 * v + bits[v]) {
            v = w;
        }

        while (v != 0) {
            join(v, 0);
            v /= 2;
        }
    }

    @Override
    public void build() {
        Arrays.fill(bits, 0);

        for (int v = size() - 1; v > 0; v--) {
            join(v, up(v));
        }
    }
}
