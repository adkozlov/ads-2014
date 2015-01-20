package ru.spbau.ads.kozlov.rangeQueries;

import java.util.*;

/**
 * @author adkozlov
 */
public abstract class AbstractRangeTree implements IHasDimension {

    private final int dimension;

    protected AbstractRangeTree left, right;
    private int[] uniqueCoordinates;
    private int origin, bound;
    protected Point[] points;

    protected AbstractRangeTree(int dimension) {
        this.dimension = dimension;
    }

    protected AbstractRangeTree(Point[] points, int dimension) {
        this(dimension);

        uniqueCoordinates = getUniqueCoordinates(points, dimension);
        Arrays.sort(uniqueCoordinates);

        Arrays.sort(points, Point.getComparator(dimension));
        buildSubTrees(0, uniqueCoordinates.length, uniqueCoordinates, getUniqueCoordinatesMap(points));
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    public AbstractRangeTree getLeft() {
        return left;
    }

    public AbstractRangeTree getRight() {
        return right;
    }

    public List<Point> query(Rectangle rectangle) {
        int from = binarySearch(uniqueCoordinates, rectangle.getBottom().getCoordinate(getDimension() - 1));
        int to = binarySearch(uniqueCoordinates, rectangle.getTop().getCoordinate(getDimension() - 1) + 1);

        if (from < to) {
            return query(from, to, rectangle, getRootLink(rectangle));
        }

        return new LinkedList<>();
    }

    public List<Point> query(int origin, int bound, Rectangle rectangle, int startLink) {
        if (origin >= this.bound || this.origin >= bound) {
            return new LinkedList<>();
        }

        if (origin <= this.origin && this.bound <= bound) {
            return innerQuery(origin, bound, rectangle, startLink);
        }

        List<Point> result = left.query(origin, bound, rectangle, getLeftLink(startLink));
        result.addAll(right.query(origin, bound, rectangle, getRightLink(startLink)));

        return result;
    }

    protected abstract int getRootLink(Rectangle rectangle);

    protected abstract int getLeftLink(int startLink);

    protected abstract int getRightLink(int startLink);

    protected abstract List<Point> innerQuery(int origin, int bound, Rectangle rectangle, int startLink);

    protected abstract AbstractRangeTree createSubTree();

    protected void buildSubTrees(int origin, int bound, int[] uniqueCoordinates,
                                 Map<Integer, List<Point>> uniqueCoordinatesMap) {
        this.origin = origin;
        this.bound = bound;
        this.uniqueCoordinates = uniqueCoordinates;

        if (origin + 1 == bound) {
            List<Point> list = uniqueCoordinatesMap.get(uniqueCoordinates[origin]);

            points = new Point[list.size()];
            for (int i = 0; i < points.length; i++) {
                points[i] = list.get(i);
            }
        } else {
            int middle = (origin + bound) / 2;

            left = createSubTree();
            right = createSubTree();

            left.buildSubTrees(origin, middle, uniqueCoordinates, uniqueCoordinatesMap);
            right.buildSubTrees(middle, bound, uniqueCoordinates, uniqueCoordinatesMap);

            points = merge(left.points, right.points, getDimension() - 1);
        }
    }

    private static int[] getUniqueCoordinates(Point[] points, int dimension) {
        Set<Integer> set = new HashSet<>();
        for (Point point : points) {
            set.add(point.getCoordinate(dimension - 1));
        }

        int[] result = new int[set.size()];
        int i = 0;
        for (int coordinate : set) {
            result[i++] = coordinate;
        }

        return result;
    }

    private Map<Integer, List<Point>> getUniqueCoordinatesMap(Point[] points) {
        Map<Integer, List<Point>> uniqueCoordinatesMap = new HashMap<>();
        for (Point point : points) {
            int coordinate = point.getCoordinate(getDimension() - 1);
            List<Point> list = uniqueCoordinatesMap.containsKey(coordinate) ?
                    uniqueCoordinatesMap.get(coordinate) : new ArrayList<Point>();

            list.add(point);
            uniqueCoordinatesMap.put(coordinate, list);
        }

        return uniqueCoordinatesMap;
    }

    private static Point[] merge(Point[] first, Point[] second, int dimension) {
        Point[] result = new Point[first.length + second.length];

        int firstIndex = 0;
        int secondIndex = 0;
        int resultIndex = 0;

        while (firstIndex < first.length && secondIndex < second.length) {
            result[resultIndex++] = Point.getComparator(dimension).compare(first[firstIndex], second[secondIndex]) < 0 ?
                    first[firstIndex++] : second[secondIndex++];
        }

        while (firstIndex < first.length) {
            result[resultIndex++] = first[firstIndex++];
        }

        while (secondIndex < second.length) {
            result[resultIndex++] = second[secondIndex++];
        }

        return result;
    }

    protected static int binarySearch(int[] coordinates, int key) {
        int left = -1;
        int right = coordinates.length;

        while (left + 1 < right) {
            int middle = (left + right) / 2;

            if (key <= coordinates[middle]) {
                left = middle;
            } else {
                right = middle;
            }
        }

        return right;
    }

    public static AbstractRangeTree createTree(Point[] points) {
        int dimension = points[0].getDimension();

        if (dimension == RangeTree2D.DIMENSION) {
            return new RangeTree2D(points);
        } else {
            return new RangeTreeKD(points, dimension);
        }
    }
}
