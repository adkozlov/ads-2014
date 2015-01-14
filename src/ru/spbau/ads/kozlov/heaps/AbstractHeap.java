package ru.spbau.ads.kozlov.heaps;

import java.util.Collection;

/**
 * @author adkozlov
 */
public abstract class AbstractHeap<T extends Comparable<T>> implements IHeap<T> {

    private final int capacity;
    private int size;
    private final Object[] values;

    public AbstractHeap(int capacity) {
        this.capacity = capacity;
        size = 0;

        values = new Object[capacity];
    }

    public AbstractHeap(Collection<T> values) {
        capacity = values.size();
        size = capacity;

        this.values = values.toArray();
    }

    protected final T set(T value, int index) {
        T result = get(index);
        values[index] = value;

        return result;
    }

    protected final T get(int index) {
        return (T) values[index];
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final boolean isEmpty() {
        return size == 0;
    }

    @Override
    public final T findMin() {
        if (isEmpty()) {
            throw new RuntimeException("The heap is empty");
        }

        return get(0);
    }

    @Override
    public final T extractMin() {
        T result = findMin();

        size--;
        T value = set(null, size());
        set(value, 0);

        siftDown(0);
        return result;
    }

    @Override
    public final void insert(T value) {
        if (size == capacity) {
            throw new RuntimeException("The heap is full");
        }

        size++;
        set(value, size - 1);
        siftUp(size - 1);
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");

        if (!isEmpty()) {
            for (int v = 0; v < size() - 1; v++) {
                builder.append(values[v]);
                builder.append(", ");
            }
            builder.append(values[size() - 1]);
        }

        builder.append("]");
        return builder.toString();
    }

    protected final void swap(int v, int w) {
        T temp = set(get(w), v);
        set(temp, w);
    }

    protected abstract void siftUp(int v);

    protected abstract void siftDown(int v);
}
