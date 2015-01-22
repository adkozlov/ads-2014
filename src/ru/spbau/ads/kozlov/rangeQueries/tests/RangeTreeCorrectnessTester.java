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
public class RangeTreeCorrectnessTester extends Tester {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        correctnessTest(Integer.parseInt(args[0]));
    }

    private static void correctnessTest(int dimension) {
        for (int pointsInside : Properties.CorrectnessTestProperties.POINTS_COUNTS) {
            for (int pointsOutside : Properties.CorrectnessTestProperties.POINTS_COUNTS) {
                System.out.printf("correctness test %s; dimension: %d, number of points inside: %d, number of points outside: %d\n",
                        correctnessTest(dimension, pointsInside, pointsOutside) ? "passed" : "failed",
                        dimension, pointsInside, pointsOutside);
            }
        }
    }

    private static boolean correctnessTest(int dimension, int pointsInsideCount, int pointsOutsideCount) {
        Rectangle rectangle = Generator.nextIntRectangle(dimension, Properties.MIN_ORIGIN, Properties.MAX_BOUND);

        List<Point> pointsInside = new ArrayList<>();
        for (int i = 0; i < pointsInsideCount; i++) {
            pointsInside.add(Generator.nextIntPoint(rectangle.getBottom(), rectangle.getTop()));
        }
        List<Point> pointsOutside = new ArrayList<>();
        for (int i = 0; i < pointsOutsideCount; i++) {
            pointsOutside.add(Generator.nextIntPointOutside(Properties.MIN_ORIGIN, Properties.MAX_BOUND, rectangle));
        }

        List<Point> points = new ArrayList<>(pointsInside);
        points.addAll(pointsOutside);

        Rectangle[] rectangles = new Rectangle[]{rectangle};

        AbstractRangeTree tree = AbstractRangeTree.createTree(points.toArray(new Point[points.size()]));
        List<Point> result = solve(tree, rectangles).get(0);

        return pointsInside.containsAll(result) && result.containsAll(pointsInside);
    }
}
