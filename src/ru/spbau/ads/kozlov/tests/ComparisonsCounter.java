package ru.spbau.ads.kozlov.tests;

/**
 * @author adkozlov
 */
public class ComparisonsCounter<T extends Comparable<T>> implements Comparable<ComparisonsCounter<T>> {

    private static long count = 0;

    private final T value;

    public ComparisonsCounter(T value) {
        this.value = value;
    }


    @Override
    public int compareTo(ComparisonsCounter<T> o) {
        count++;

        return value.compareTo(o.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static long getCount() {
        return count;
    }

    public static void refreshCounter() {
        count = 0;
    }
}
