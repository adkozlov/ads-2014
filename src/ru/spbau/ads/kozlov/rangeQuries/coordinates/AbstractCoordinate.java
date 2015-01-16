package ru.spbau.ads.kozlov.rangeQuries.coordinates;

/**
 * @author adkozlov
 */
public abstract class AbstractCoordinate<C extends Comparable<C>> implements ICoordinate<C> {

    private final C value;

    public AbstractCoordinate(C value) {
        if (value == null) {
            throw new IllegalArgumentException("Value should be not null");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCoordinate)) return false;

        AbstractCoordinate that = (AbstractCoordinate) o;

        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public C getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(ICoordinate<C> coordinate) {
        return value.compareTo(coordinate.getValue());
    }
}
