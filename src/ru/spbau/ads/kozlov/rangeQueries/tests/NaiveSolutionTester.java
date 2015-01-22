package ru.spbau.ads.kozlov.rangeQueries.tests;

import ru.spbau.ads.kozlov.rangeQueries.Point;
import ru.spbau.ads.kozlov.rangeQueries.Rectangle;

import java.util.Locale;

/**
 * @author adkozlov
 */
public class NaiveSolutionTester extends Tester {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        asymptoticTest(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Float.parseFloat(args[2]));
    }

    private static void asymptoticTest(int dimension, int pointsCount, float percent) {
        Point[] points = new Point[pointsCount];
        for (int i = 0; i < points.length; i++) {
            points[i] = Generator.nextIntPoint(dimension, Properties.MIN_ORIGIN, Properties.MAX_BOUND);
        }

        Rectangle[] rectangles = new Rectangle[Properties.AsymptoticTestProperties.RECTANGLES_COUNT];
        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i] = Generator.nextIntRectangle(dimension,
                    (int) (Properties.MIN_ORIGIN * percent), (int) (Properties.MAX_BOUND * percent));
        }

        long timestamp = System.currentTimeMillis();
        naiveSolve(points, rectangles);
        timestamp = System.currentTimeMillis() - timestamp;

        System.out.printf("dimension: %s, number of points: %d, query duration: %.3f\n",
                dimension, pointsCount, (double) timestamp / rectangles.length);
        //System.out.printf("(%d,%.2f),", pointsCount, (double) timestamp / rectangles.length);
    }
}
