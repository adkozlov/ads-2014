package ru.spbau.ads.kozlov.heaps;

/**
 * @author adkozlov
 */
public interface IHeap<T extends Comparable<T>> {

    int size();

    boolean isEmpty();

    T findMin();

    T extractMin();

    void insert(T value);

    void build();
}
