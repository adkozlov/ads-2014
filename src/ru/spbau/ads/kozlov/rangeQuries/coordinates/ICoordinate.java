package ru.spbau.ads.kozlov.rangeQuries.coordinates;

/**
 * @author adkozlov
 */
public interface ICoordinate<C extends Comparable<C>> extends Comparable<ICoordinate<C>> {

    C getValue();

    ICoordinate<C> nextCoordinate();
}
