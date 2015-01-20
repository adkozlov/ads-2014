package ru.spbau.ads.kozlov.rangeQueries;

import java.util.*;

/**
 * @author adkozlov
 */
public class RangeTree2D extends AbstractRangeTree {

    public static final int DIMENSION = 2;

    private int[] leftLinks, rightLinks, firstCoordinates;

    protected RangeTree2D() {
        super(DIMENSION);
    }

    protected RangeTree2D(Point[] points) {
        super(points, DIMENSION);
    }

    @Override
    protected List<Point> innerQuery(int origin, int bound, Rectangle rectangle, int startLink) {
        List<Point> result = new LinkedList<>();

        int coordinate = rectangle.getTop().getCoordinate(0);
        while (linkIsValid(startLink, points.length) && points[startLink].getCoordinate(0) <= coordinate) {
            result.add(points[startLink++]);
        }

        return result;
    }

    @Override
    protected RangeTree2D createSubTree() {
        return new RangeTree2D();
    }

    @Override
    public RangeTree2D getLeft() {
        return (RangeTree2D) super.getLeft();
    }

    @Override
    public RangeTree2D getRight() {
        return (RangeTree2D) super.getRight();
    }

    @Override
    protected void buildSubTrees(int origin, int bound, int[] uniqueCoordinates, Map<Integer, List<Point>> uniqueCoordinatesMap) {
        super.buildSubTrees(origin, bound, uniqueCoordinates, uniqueCoordinatesMap);

        firstCoordinates = new int[points.length];
        for (int i = 0; i < firstCoordinates.length; i++) {
            firstCoordinates[i] = points[i].getCoordinate(0);
        }

        if (left != null) {
            leftLinks = createLinks(getLeft());
        }
        if (right != null) {
            rightLinks = createLinks(getRight());
        }
    }

    private int[] createLinks(RangeTree2D subTree) {
        int[] result = new int[points.length];
        Arrays.fill(result, -1);

        int link = 0;
        for (int i = 0; i < points.length; i++) {
            boolean found = false;
            for (int j = link; j < subTree.firstCoordinates.length; j++) {
                if (firstCoordinates[i] <= subTree.firstCoordinates[j]) {
                    link = j;
                    result[i] = link;

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

    @Override
    protected int getRootLink(Rectangle rectangle) {
        return binarySearch(firstCoordinates, rectangle.getBottom().getCoordinate(0));
    }

    @Override
    protected int getLeftLink(int startLink) {
        return getLink(leftLinks, startLink);
    }

    @Override
    protected int getRightLink(int startLink) {
        return getLink(rightLinks, startLink);
    }

    private static int getLink(int[] links, int startLink) {
        return links != null && linkIsValid(startLink, links.length) ? links[startLink] : -1;
    }

    private static boolean linkIsValid(int startLink, int length) {
        return startLink != -1 && startLink < length;
    }
}
