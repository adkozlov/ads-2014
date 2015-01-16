package ru.spbau.ads.kozlov.rangeQuries;

import ru.spbau.ads.kozlov.rangeQuries.coordinates.ICoordinate;

/**
 * @author adkozlov
 */
public class Rectangle<T extends Comparable<T>, C extends ICoordinate<T>> implements IHasDimension {

    private final Point<T, C> bottom, top;

    public Rectangle(Point<T, C> bottom, Point<T, C> top) {
        if (bottom.getDimension() != top.getDimension()) {
            throw new IllegalArgumentException("Dimensions of points should be equal");
        }

        this.bottom = bottom;
        this.top = top;
    }

    public Point<T, C> getBottom() {
        return bottom;
    }

    public Point<T, C> getTop() {
        return top;
    }

    @Override
    public int getDimension() {
        return bottom.getDimension();
    }

    public boolean isInside(Point<T, C> point) {
        for (int i = 0; i < point.getDimension(); i++) {
            if (!coordinateIsInside(point, i)) {
                return false;
            }
        }

        return true;
    }

    private boolean coordinateIsInside(Point<T, C> point, int index) {
        C coordinate = point.getCoordinate(index);

        return bottom.getCoordinate(index).compareTo(coordinate) <= 0 &&
                coordinate.compareTo(top.getCoordinate(index)) <= 0;
    }

    @Override
    public String toString() {
        return "(" + bottom + ", " + top + ")";
    }
}
