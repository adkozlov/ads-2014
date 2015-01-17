package ru.spbau.ads.kozlov.rangeQuries.tests;

import ru.spbau.ads.kozlov.rangeQuries.AbstractRangeTree;
import ru.spbau.ads.kozlov.rangeQuries.Point;
import ru.spbau.ads.kozlov.rangeQuries.Rectangle;
import ru.spbau.ads.kozlov.rangeQuries.coordinates.IntegerCoordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adkozlov
 */
public class Tester {

    private static class CorrectnessTestProperties {
        public static final int MIN_DIMENSION = 2;
        public static final int MAX_DIMENSION = 5;

        public static final int ORIGIN = 0;
        public static final int BOUND = 100;

        public static final int[] POINTS_COUNTS = new int[]{8, 16, 32, 64, 128};
    }

    private static class AsymptoticTestProperties {
        public static final int DIMENSION = 3;

        public static final int MIN_ORIGIN = -10000;
        public static final int MAX_BOUND = 10000;

        public static final int MAX_POINTS_COUNT = 1000000;
        public static final int[] POINTS_COUNTS = new int[]{10000, 20000, 30000, 40000, 50000,
                100000, 200000, 300000, 400000};
        public static final int RECTANGLES_COUNT = 5;
    }

    public static void main(String[] args) {
        //correctnessTest();
        asymptoticTest();
    }

    private static void asymptoticTest() {
        List<Point<Integer, IntegerCoordinate>> points = new ArrayList<>();
        for (int i = 0; i < AsymptoticTestProperties.MAX_POINTS_COUNT; i++) {
            points.add(Generator.nextIntPoint(AsymptoticTestProperties.DIMENSION, AsymptoticTestProperties.MIN_ORIGIN,
                    AsymptoticTestProperties.MAX_BOUND));
        }

        List<Rectangle<Integer, IntegerCoordinate>> rectangles = new ArrayList<>();
        for (int i = 0; i < AsymptoticTestProperties.RECTANGLES_COUNT; i++) {
            rectangles.add(Generator.nextIntRectangle(AsymptoticTestProperties.DIMENSION,
                    AsymptoticTestProperties.MIN_ORIGIN, AsymptoticTestProperties.MAX_BOUND));
        }

        for (int pointsCount : AsymptoticTestProperties.POINTS_COUNTS) {
            List<Point<Integer, IntegerCoordinate>> subList = points.subList(0, pointsCount);

            long timestamp = System.currentTimeMillis();
            AbstractRangeTree<Integer, IntegerCoordinate> tree = AbstractRangeTree.createTree(subList);
            System.out.println("number of points: " + pointsCount + ", build time: " + (System.currentTimeMillis() - timestamp));

            long[] result = new long[2];
            timestamp = System.currentTimeMillis();
            solve(tree, rectangles);
            result[0] = System.currentTimeMillis() - timestamp;
            result[0] /= rectangles.size();
            System.out.print("\ttree: " + result[0]);

            timestamp = System.currentTimeMillis();
            naiveSolve(subList, rectangles);
            result[1] = System.currentTimeMillis() - timestamp;
            result[1] /= rectangles.size();

            System.out.println(", simple: " + result[1]);
        }
    }

    static void correctnessTest() {
        for (int dimension = CorrectnessTestProperties.MIN_DIMENSION; dimension <= CorrectnessTestProperties.MAX_DIMENSION; dimension++) {
            for (int pointsInside : CorrectnessTestProperties.POINTS_COUNTS) {
                for (int pointsOutside : CorrectnessTestProperties.POINTS_COUNTS) {
                    System.out.printf("Correctness test %s; dimension: %d, points inside: %d, points outside: %d\n",
                            correctnessTest(dimension, pointsInside, pointsOutside) ? "passed" : "failed",
                            dimension, pointsInside, pointsOutside);
                }
            }
        }

        System.out.println();
    }

    private static boolean correctnessTest(int dimension, int pointsInsideCount, int pointsOutsideCount) {
        Rectangle<Integer, IntegerCoordinate> rectangle = Generator.nextIntRectangle(dimension,
                CorrectnessTestProperties.ORIGIN, CorrectnessTestProperties.BOUND);

        List<Point<Integer, IntegerCoordinate>> pointsInside = new ArrayList<>();
        for (int i = 0; i < pointsInsideCount; i++) {
            pointsInside.add(Generator.nextIntPoint(rectangle.getBottom(), rectangle.getTop()));
        }
        List<Point<Integer, IntegerCoordinate>> pointsOutside = new ArrayList<>();
        for (int i = 0; i < pointsOutsideCount; i++) {
            pointsOutside.add(Generator.nextIntPoint(dimension, CorrectnessTestProperties.ORIGIN,
                    CorrectnessTestProperties.BOUND, rectangle));
        }

        List<Point<Integer, IntegerCoordinate>> points = new ArrayList<>(pointsInside);
        points.addAll(pointsOutside);

        List<Rectangle<Integer, IntegerCoordinate>> rectangles = new ArrayList<>();
        rectangles.add(rectangle);

        List<Point<Integer, IntegerCoordinate>> result = solve(AbstractRangeTree.createTree(points), rectangles).get(0);
        return pointsInside.containsAll(result) && result.containsAll(pointsInside);
    }

    public static List<List<Point<Integer, IntegerCoordinate>>> solve(AbstractRangeTree<Integer, IntegerCoordinate> tree,
                                                                      List<Rectangle<Integer, IntegerCoordinate>> rectangles) {
        List<List<Point<Integer, IntegerCoordinate>>> result = new ArrayList<>();
        for (Rectangle<Integer, IntegerCoordinate> rectangle : rectangles) {
            result.add(solve(tree, rectangle));
        }

        return result;
    }

    public static List<Point<Integer, IntegerCoordinate>> solve(AbstractRangeTree<Integer, IntegerCoordinate> tree,
                                                                Rectangle<Integer, IntegerCoordinate> rectangle) {
        return tree.query(rectangle);
    }

    public static List<List<Point<Integer, IntegerCoordinate>>> naiveSolve(List<Point<Integer, IntegerCoordinate>> points,
                                                                           List<Rectangle<Integer, IntegerCoordinate>> rectangles) {
        List<List<Point<Integer, IntegerCoordinate>>> result = new ArrayList<>();

        for (Rectangle<Integer, IntegerCoordinate> rectangle : rectangles) {
            result.add(naiveSolve(points, rectangle));
        }

        return result;
    }

    private static List<Point<Integer, IntegerCoordinate>> naiveSolve(List<Point<Integer, IntegerCoordinate>> points,
                                                                      Rectangle<Integer, IntegerCoordinate> rectangle) {
        List<Point<Integer, IntegerCoordinate>> result = new ArrayList<>();

        for (Point<Integer, IntegerCoordinate> point : points) {
            if (rectangle.isInside(point)) {
                result.add(point);
            }
        }

        return result;
    }
}