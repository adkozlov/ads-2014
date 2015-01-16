package ru.spbau.ads.kozlov.rangeQuries.coordinates;

/**
 * @author adkozlov
 */
public class IntegerCoordinate extends AbstractCoordinate<Integer> {

    public IntegerCoordinate(int value) {
        super(value);
    }

    @Override
    public IntegerCoordinate nextCoordinate() {
        return new IntegerCoordinate(getValue() + 1);
    }
}
