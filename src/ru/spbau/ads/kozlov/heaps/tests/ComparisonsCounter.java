package ru.spbau.ads.kozlov.heaps.tests;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComparisonsCounter)) return false;

        ComparisonsCounter that = (ComparisonsCounter) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public static long getCount() {
        return count;
    }

    public static void refreshCounter() {
        count = 0;
    }

    public static <T extends Comparable<T>> List<ComparisonsCounter<T>> convert(List<T> values) {
        List<ComparisonsCounter<T>> result = new ArrayList<>();

        for (T value : values) {
            result.add(new ComparisonsCounter<>(value));
        }

        return result;
    }
}
