package ru.spbau.ads.kozlov.rangeQuries;

import ru.spbau.ads.kozlov.rangeQuries.coordinates.ICoordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author adkozlov
 */
public class RangeTree2D<T extends Comparable<T>, C extends ICoordinate<T>> extends AbstractRangeTree<T, C> {

    public static final int DIMENSION = 2;

    private List<Integer> leftLinks, rightLinks;

    protected RangeTree2D() {
        super(DIMENSION);
    }

    protected RangeTree2D(List<Point<T, C>> points) {
        super(DIMENSION, points);
    }

    @Override
    protected void buildTree(int origin, int bound, List<C> uniqueCoordinates,
                             Map<C, ArrayList<Point<T, C>>> uniqueCoordinatesMap) {
        buildSubTrees(origin, bound, uniqueCoordinates, uniqueCoordinatesMap);

        if (left != null) {
            leftLinks = createLinks(getPoints(), left.getPoints());
        }
        if (right != null) {
            rightLinks = createLinks(getPoints(), right.getPoints());
        }
    }

    @Override
    protected int getRootLink(C bottom) {
        List<C> coordinates = new ArrayList<>();
        for (Point<T, C> point : getPoints()) {
            coordinates.add(point.getFirstCoordinate());
        }

        return binarySearch(coordinates, bottom);
    }

    @Override
    protected int getLeftLink(int startLink) {
        return getLink(leftLinks, startLink);
    }

    @Override
    protected int getRightLink(int startLink) {
        return getLink(rightLinks, startLink);
    }

    @Override
    protected RangeTree2D<T, C> createSubTree() {
        return new RangeTree2D<>();
    }

    @Override
    protected ArrayList<Point<T, C>> innerQuery(int origin, int bound, Rectangle<T, C> rectangle, int startLink) {
        ArrayList<Point<T, C>> result = new ArrayList<>();

        List<Point<T, C>> points = getPoints();
        while (startLink != -1 && startLink < points.size() &&
                points.get(startLink).getFirstCoordinate().compareTo(rectangle.getTop().getFirstCoordinate()) <= 0) {
            result.add(points.get(startLink));
            startLink++;
        }

        return result;
    }

    private static int getLink(List<Integer> links, int startLink) {
        return startLink != -1 && startLink < links.size() ? links.get(startLink) : -1;
    }

    private static <T extends Comparable<T>, C extends ICoordinate<T>> List<Integer> createLinks(
            List<Point<T, C>> parent, List<Point<T, C>> child) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < parent.size(); i++) {
            result.add(-1);
        }
        int link = 0;

        for (int i = 0; i < parent.size(); i++) {
            boolean found = false;
            for (int j = link; j < child.size(); j++) {
                if (child.get(j).getFirstCoordinate().compareTo(parent.get(i).getFirstCoordinate()) >= 0) {
                    link = j;
                    result.set(i, link);

                    found = true;
                    break;
                }
            }

            if (!found) {
                break;
            }
        }

        return result;
    }
}
