package ru.spbau.ads.kozlov.rangeQueries;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author adkozlov
 */
public class Point implements IHasDimension {

    private final int[] coordinates;

    public Point(int[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int getDimension() {
        return coordinates.length;
    }

    public int getCoordinate(int index) {
        return coordinates[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        return Arrays.equals(coordinates, point.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }

    public static Comparator<Point> getComparator(final int dimension) {
        return new Comparator<Point>() {
            @Override
            public int compare(Point first, Point second) {
                int result = Integer.compare(first.getCoordinate(dimension - 1), second.getCoordinate(dimension - 1));

                return dimension > 1 && result == 0
                        ? Integer.compare(first.getCoordinate(dimension - 2), second.getCoordinate(dimension - 2))
                        : result;
            }
        };
    }
}
