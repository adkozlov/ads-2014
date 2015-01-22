package ru.spbau.ads.kozlov.rangeQueries.tests;

import ru.spbau.ads.kozlov.rangeQueries.AbstractRangeTree;
import ru.spbau.ads.kozlov.rangeQueries.Point;
import ru.spbau.ads.kozlov.rangeQueries.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author adkozlov
 */
public abstract class Tester {

    protected static List<List<Point>> solve(AbstractRangeTree tree, Rectangle[] rectangles) {
        List<List<Point>> result = new LinkedList<>();
        for (Rectangle rectangle : rectangles) {
            result.add(solve(tree, rectangle));
        }

        return result;
    }

    protected static List<Point> solve(AbstractRangeTree tree, Rectangle rectangle) {
        return tree.query(rectangle);
    }

    protected static List<List<Point>> naiveSolve(Point[] points, Rectangle[] rectangles) {
        List<List<Point>> result = new ArrayList<>();

        for (Rectangle rectangle : rectangles) {
            result.add(naiveSolve(points, rectangle));
        }

        return result;
    }

    protected static List<Point> naiveSolve(Point[] points, Rectangle rectangle) {
        List<Point> result = new ArrayList<>();

        for (Point point : points) {
            if (rectangle.contains(point)) {
                result.add(point);
            }
        }

        return result;
    }
}
