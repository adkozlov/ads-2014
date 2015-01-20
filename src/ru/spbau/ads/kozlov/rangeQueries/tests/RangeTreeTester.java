package ru.spbau.ads.kozlov.rangeQueries.tests;

import ru.spbau.ads.kozlov.rangeQueries.AbstractRangeTree;
import ru.spbau.ads.kozlov.rangeQueries.Point;
import ru.spbau.ads.kozlov.rangeQueries.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author adkozlov
 */
public class RangeTreeTester {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        for (int dimension = Properties.MIN_DIMENSION; dimension <= Properties.MAX_DIMENSION; dimension++) {
            correctnessTest(dimension);
        }

        asymptoticTest(args.length > 0 ? Integer.parseInt(args[0]) : (Properties.MIN_DIMENSION + Properties.MAX_DIMENSION) / 2);
    }

    private static void correctnessTest(int dimension) {
        for (int pointsInside : Properties.CorrectnessTestProperties.POINTS_COUNTS) {
            for (int pointsOutside : Properties.CorrectnessTestProperties.POINTS_COUNTS) {
                System.out.printf("correctness test %s; dimension: %d, number of points inside: %d, number of points outside: %d\n",
                        correctnessTest(dimension, pointsInside, pointsOutside) ? "passed" : "failed",
                        dimension, pointsInside, pointsOutside);
            }
        }

        System.out.println();
    }

    private static boolean correctnessTest(int dimension, int pointsInsideCount, int pointsOutsideCount) {
        Rectangle rectangle = Generator.nextIntRectangle(dimension, Properties.MIN_ORIGIN, Properties.MAX_BOUND);

        List<Point> pointsInside = new ArrayList<>();
        for (int i = 0; i < pointsInsideCount; i++) {
            pointsInside.add(Generator.nextIntPoint(rectangle.getBottom(), rectangle.getTop()));
        }
        List<Point> pointsOutside = new ArrayList<>();
        for (int i = 0; i < pointsOutsideCount; i++) {
            pointsOutside.add(Generator.nextIntPoint(Properties.MIN_ORIGIN, Properties.MAX_BOUND, rectangle));
        }

        List<Point> points = new ArrayList<>(pointsInside);
        points.addAll(pointsOutside);

        Rectangle[] rectangles = new Rectangle[]{rectangle};

        AbstractRangeTree tree = AbstractRangeTree.createTree(points.toArray(new Point[points.size()]));
        List<Point> result = solve(tree, rectangles).get(0);

        return pointsInside.containsAll(result) && result.containsAll(pointsInside);
    }

    private static void asymptoticTest(int dimension) {
        for (int pointsCount : Properties.AsymptoticTestProperties.POINTS_COUNTS) {
            Point[] points = new Point[pointsCount];
            for (int i = 0; i < points.length; i++) {
                points[i] = Generator.nextIntPoint(dimension, Properties.MIN_ORIGIN, Properties.MAX_BOUND);
            }

            Rectangle[] rectangles = new Rectangle[Properties.AsymptoticTestProperties.RECTANGLES_COUNT];
            for (int i = 0; i < rectangles.length; i++) {
                rectangles[i] = Generator.nextIntRectangle(dimension, Properties.MIN_ORIGIN, Properties.MAX_BOUND);
            }

            System.out.printf("dimension: %s, number of points: %d, ", dimension, pointsCount);

            long timestamp = System.currentTimeMillis();
            AbstractRangeTree tree = AbstractRangeTree.createTree(points);
            System.out.printf("build time: %d, ", System.currentTimeMillis() - timestamp);

            timestamp = System.currentTimeMillis();
            solve(tree, rectangles);
            timestamp = (System.currentTimeMillis() - timestamp);
            System.out.printf("query duration: %.2f\n", (double) timestamp / rectangles.length);

            System.gc();
        }
    }

    private static List<List<Point>> solve(AbstractRangeTree tree, Rectangle[] rectangles) {
        List<List<Point>> result = new ArrayList<>();
        for (Rectangle rectangle : rectangles) {
            result.add(solve(tree, rectangle));
        }

        return result;
    }

    private static List<Point> solve(AbstractRangeTree tree, Rectangle rectangle) {
        return tree.query(rectangle);
    }
}