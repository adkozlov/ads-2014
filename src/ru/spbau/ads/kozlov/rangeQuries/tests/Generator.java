package ru.spbau.ads.kozlov.rangeQuries.tests;

import ru.spbau.ads.kozlov.rangeQuries.Point;
import ru.spbau.ads.kozlov.rangeQuries.Rectangle;
import ru.spbau.ads.kozlov.rangeQuries.coordinates.IntegerCoordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author adkozlov
 */
public class Generator {

    private static final Random RANDOM = new Random();

    public static Point<Integer, IntegerCoordinate> nextIntPoint(int dimension, int origin, int bound) {
        return new Point<>(nextIntegers(dimension, origin, bound));
    }

    public static Point<Integer, IntegerCoordinate> nextIntPoint(Point<Integer, IntegerCoordinate> bottom,
                                                                 Point<Integer, IntegerCoordinate> top) {
        int dimension = bottom.getDimension();

        List<IntegerCoordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            coordinates.add(new IntegerCoordinate(
                    bottom.getCoordinate(i).getValue() + RANDOM.nextInt(top.getCoordinate(i).getValue() - bottom.getCoordinate(i).getValue() + 1)));
        }

        return new Point<>(coordinates);
    }

    private static List<IntegerCoordinate> nextIntegers(int dimension, int origin, int bound) {
        List<IntegerCoordinate> result = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            result.add(new IntegerCoordinate(origin + RANDOM.nextInt(bound)));
        }

        return result;
    }

    public static Rectangle<Integer, IntegerCoordinate> nextIntRectangle(int dimension, int origin, int bound) {
        Point<Integer, IntegerCoordinate> bottom = nextIntPoint(dimension, origin, bound);

        List<IntegerCoordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            coordinates.add(new IntegerCoordinate(bound));
        }
        Point<Integer, IntegerCoordinate> top = nextIntPoint(bottom, new Point<>(coordinates));

        return new Rectangle<>(bottom, top);
    }

    public static Point<Integer, IntegerCoordinate> nextIntPoint(int dimension, int origin, int bound,
                                                                 Rectangle<Integer, IntegerCoordinate> rectangle) {
        Point<Integer, IntegerCoordinate> result = nextIntPoint(dimension, origin, bound);

        while (rectangle.isInside(result)) {
            result = nextIntPoint(dimension, origin, bound);
        }
        return result;
    }
}
