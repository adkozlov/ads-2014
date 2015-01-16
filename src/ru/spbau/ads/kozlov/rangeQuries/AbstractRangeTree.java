package ru.spbau.ads.kozlov.rangeQuries;

import ru.spbau.ads.kozlov.rangeQuries.coordinates.ICoordinate;

import java.util.*;

/**
 * @author adkozlov
 */
public abstract class AbstractRangeTree<T extends Comparable<T>, C extends ICoordinate<T>> implements IHasDimension {

    private final int dimension;

    protected AbstractRangeTree<T, C> left, right;
    private int origin, bound;
    private List<C> uniqueCoordinates;
    private List<Point<T, C>> points;

    protected AbstractRangeTree(int dimension) {
        this.dimension = dimension;
    }

    protected AbstractRangeTree(int dimension, List<Point<T, C>> points) {
        this(dimension);

        Set<C> set = uniqueCoordinates(points, dimension);
        uniqueCoordinates = new ArrayList<>(set);

        Comparator<Point<T, C>> comparator = Point.getComparator(dimension);
        Collections.sort(points, comparator);

        Map<C, ArrayList<Point<T, C>>> map = emptyUniqueCoordinatesMap(set);
        for (Point<T, C> point : points) {
            map.get(point.getCoordinate(dimension - 1)).add(point);
        }

        buildTree(0, uniqueCoordinates.size(), uniqueCoordinates, map);
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    public List<Point<T, C>> getPoints() {
        return points;
    }

    protected abstract void buildTree(int origin, int bound, List<C> uniqueCoordinates,
                                      Map<C, ArrayList<Point<T, C>>> uniqueCoordinatesMap);

    protected abstract AbstractRangeTree<T, C> createSubTree();

    protected void buildSubTrees(int origin, int bound, List<C> uniqueCoordinates,
                                 Map<C, ArrayList<Point<T, C>>> uniqueCoordinatesMap) {
        this.origin = origin;
        this.bound = bound;
        this.uniqueCoordinates = uniqueCoordinates;

        if (origin + 1 < bound) {
            int middle = (origin + bound) / 2;

            left = createSubTree();
            right = createSubTree();

            left.buildTree(origin, middle, uniqueCoordinates, uniqueCoordinatesMap);
            right.buildTree(middle, bound, uniqueCoordinates, uniqueCoordinatesMap);

            points = mergeIterators(getDimension() - 1, left.points.iterator(), right.points.iterator());
        } else {
            points = new ArrayList<>(uniqueCoordinatesMap.get(uniqueCoordinates.get(origin)));
        }
    }

    protected int getRootLink(C bottom) {
        return -1;
    }

    protected int getLeftLink(int startLink) {
        return -1;
    }

    protected int getRightLink(int startLink) {
        return -1;
    }

    public ArrayList<Point<T, C>> query(int origin, int bound, Rectangle<T, C> rectangle, int startLink) {
        if (origin >= this.bound || this.origin >= bound) {
            return new ArrayList<>();
        }

        if (origin <= this.origin && this.bound <= bound) {
            return innerQuery(origin, bound, rectangle, startLink);
        }

        ArrayList<Point<T, C>> result = left.query(origin, bound, rectangle, getLeftLink(startLink));
        result.addAll(right.query(origin, bound, rectangle, getRightLink(startLink)));

        return result;
    }

    protected abstract ArrayList<Point<T, C>> innerQuery(int origin, int bound, Rectangle<T, C> rectangle, int startLink);

    public ArrayList<Point<T, C>> query(Rectangle<T, C> rectangle) {
        int index = getDimension() - 1;

        int origin = binarySearch(uniqueCoordinates, rectangle.getBottom().getCoordinate(index));
        int bound = binarySearch(uniqueCoordinates, (C) rectangle.getTop().getCoordinate(index).nextCoordinate());

        if (origin < bound) {
            return query(origin, bound, rectangle, getRootLink(rectangle.getBottom().getFirstCoordinate()));
        }

        return new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        appendSubTreeToStringBuilder(left, builder);
        builder.append(points);
        appendSubTreeToStringBuilder(right, builder);

        return builder.toString();
    }

    private static void appendSubTreeToStringBuilder(AbstractRangeTree tree, StringBuilder builder) {
        if (tree != null) {
            builder.append(tree.toString());
        }
    }

    private static <T extends Comparable<T>, C extends ICoordinate<T>> Set<C> uniqueCoordinates(
            List<Point<T, C>> points, int dimension) {
        Set<C> result = new TreeSet<>();
        for (Point<T, C> point : points) {
            result.add(point.getCoordinate(dimension - 1));
        }

        return result;
    }

    private static <T extends Comparable<T>, C extends ICoordinate<T>> Map<C, ArrayList<Point<T, C>>>
    emptyUniqueCoordinatesMap(Set<C> coordinates) {
        Map<C, ArrayList<Point<T, C>>> result = new HashMap<>();
        for (C coordinate : coordinates) {
            result.put(coordinate, new ArrayList<Point<T, C>>());
        }

        return result;
    }

    public static <T extends Comparable<T>, C extends ICoordinate<T>> AbstractRangeTree<T, C>
    createTree(List<Point<T, C>> points) {
        if (points.isEmpty()) {
            return null;
        }

        int dimension = points.get(0).getDimension();
        return dimension > 2 ? new RangeTreeKD<>(dimension, points) : new RangeTree2D<>(points);
    }

    public static <T extends Comparable<T>, C extends ICoordinate<T>> List<Point<T, C>> mergeIterators(
            int dimension, Iterator<Point<T, C>> first, Iterator<Point<T, C>> second) {
        List<Point<T, C>> result = new ArrayList<>();

        Point<T, C> firstPoint = nullableNext(first);
        Point<T, C> secondPoint = nullableNext(second);

        while (firstPoint != null && secondPoint != null) {
            Comparator<Point<T, C>> comparator = Point.getComparator(dimension);

            if (comparator.compare(firstPoint, secondPoint) < 0) {
                result.add(firstPoint);
                firstPoint = nullableNext(first);
            } else {
                result.add(secondPoint);
                secondPoint = nullableNext(second);
            }
        }

        addIteratorTailToList(firstPoint, first, result);
        addIteratorTailToList(secondPoint, second, result);

        return result;
    }

    private static <T extends Comparable<T>, C extends ICoordinate<T>> void addIteratorTailToList(
            Point<T, C> point, Iterator<Point<T, C>> iterator, List<Point<T, C>> result) {
        if (point != null) {
            result.add(point);

            while (iterator.hasNext()) {
                result.add(iterator.next());
            }
        }
    }

    private static <T extends Comparable<T>, C extends ICoordinate<T>> Point<T, C> nullableNext(
            Iterator<Point<T, C>> iterator) {
        return iterator.hasNext() ? iterator.next() : null;
    }

    protected static <T extends Comparable<T>, C extends ICoordinate<T>> int binarySearch(List<C> coordinates, C key) {
        int left = -1;
        int right = coordinates.size();

        while (left + 1 < right) {
            int middle = (left + right) / 2;

            if (coordinates.get(middle).compareTo(key) >= 0) {
                right = middle;
            } else {
                left = middle;
            }
        }

        return right;
    }
}
