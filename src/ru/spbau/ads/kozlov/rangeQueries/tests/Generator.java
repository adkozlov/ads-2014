package ru.spbau.ads.kozlov.rangeQueries.tests;

import ru.spbau.ads.kozlov.rangeQueries.Point;
import ru.spbau.ads.kozlov.rangeQueries.Rectangle;

import java.util.Random;

/**
 * @author adkozlov
 */
public class Generator {

    private static final Random RANDOM = new Random();

    public static Point nextIntPoint(Point bottom, Point top) {
        int[] coordinates = new int[bottom.getDimension()];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = bottom.getCoordinate(i) + RANDOM.nextInt(top.getCoordinate(i) - bottom.getCoordinate(i) + 1);
        }

        return new Point(coordinates);
    }

    public static Point nextIntPoint(int dimension, int origin, int bound) {
        int[] coordinates = new int[dimension];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = origin + RANDOM.nextInt(bound);
        }

        return new Point(coordinates);
    }

    public static Point nextIntPoint(int origin, int bound, Rectangle rectangle) {
        int dimension = rectangle.getDimension();
        Point result = nextIntPoint(dimension, origin, bound);

        while (rectangle.contains(result)) {
            result = nextIntPoint(dimension, origin, bound);
        }

        return result;
    }

    public static Rectangle nextIntRectangle(int dimension, int origin, int bound) {
        Point bottom = nextIntPoint(dimension, origin, bound);

        int[] coordinates = new int[dimension];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = bound;
        }
        Point top = nextIntPoint(bottom, new Point(coordinates));

        return new Rectangle(bottom, top);
    }
}
