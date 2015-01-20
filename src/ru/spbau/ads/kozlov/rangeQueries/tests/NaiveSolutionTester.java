package ru.spbau.ads.kozlov.rangeQueries.tests;

import ru.spbau.ads.kozlov.rangeQueries.Point;
import ru.spbau.ads.kozlov.rangeQueries.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author adkozlov
 */
public class NaiveSolutionTester {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        for (int dimension = Properties.MIN_DIMENSION; dimension <= Properties.MAX_DIMENSION; dimension++) {
            asymptoticTest(dimension);
        }
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

            long timestamp = System.currentTimeMillis();
            naiveSolve(points, rectangles);
            timestamp = System.currentTimeMillis() - timestamp;

            System.out.printf("dimension: %s, number of points: %d, query duration: %.2f\n",
                    dimension, pointsCount, (double) timestamp / rectangles.length);
        }
    }

    private static List<List<Point>> naiveSolve(Point[] points, Rectangle[] rectangles) {
        List<List<Point>> result = new ArrayList<>();

        for (Rectangle rectangle : rectangles) {
            result.add(naiveSolve(points, rectangle));
        }

        return result;
    }

    private static List<Point> naiveSolve(Point[] points, Rectangle rectangle) {
        List<Point> result = new ArrayList<>();

        for (Point point : points) {
            if (rectangle.contains(point)) {
                result.add(point);
            }
        }

        return result;
    }
}
