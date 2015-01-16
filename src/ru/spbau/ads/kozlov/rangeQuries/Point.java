package ru.spbau.ads.kozlov.rangeQuries;

import ru.spbau.ads.kozlov.rangeQuries.coordinates.ICoordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author adkozlov
 */
public class Point<T extends Comparable<T>, C extends ICoordinate<T>> implements IHasDimension {

    private final List<C> coordinates;

    public Point(List<C> coordinates) {
        if (coordinates.isEmpty()) {
            throw new IllegalArgumentException("Point should have at least one coordinate");
        }

        this.coordinates = Collections.unmodifiableList(new ArrayList<>(coordinates));
    }

    @Override
    public int getDimension() {
        return coordinates.size();
    }

    public C getCoordinate(int index) {
        return coordinates.get(index);
    }

    public C getFirstCoordinate() {
        return getCoordinate(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        return coordinates.equals(point.coordinates);

    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }

    @Override
    public String toString() {
        return coordinates.toString();
    }

    public static <T extends Comparable<T>, C extends ICoordinate<T>> Comparator<Point<T, C>> getComparator(final int dimension) {
        return new Comparator<Point<T, C>>() {
            @Override
            public int compare(Point<T, C> first, Point<T, C> second) {
                int result = first.getCoordinate(dimension - 1).compareTo(second.getCoordinate(dimension - 1));

                return dimension > 1 && result == 0 ?
                        first.getCoordinate(dimension - 2).compareTo(second.getCoordinate(dimension - 2)) :
                        result;
            }
        };
    }
}
