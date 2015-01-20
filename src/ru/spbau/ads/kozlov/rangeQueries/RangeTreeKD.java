package ru.spbau.ads.kozlov.rangeQueries;

import java.util.List;
import java.util.Map;

/**
 * @author adkozlov
 */
public class RangeTreeKD extends AbstractRangeTree {

    private AbstractRangeTree subTree;

    protected RangeTreeKD(int dimension) {
        super(dimension);
    }

    protected RangeTreeKD(Point[] points, int dimension) {
        super(points, dimension);
    }

    @Override
    protected List<Point> innerQuery(int origin, int bound, Rectangle rectangle, int startLink) {
        return subTree.query(rectangle);
    }

    @Override
    protected AbstractRangeTree createSubTree() {
        return new RangeTreeKD(getDimension());
    }

    @Override
    protected void buildSubTrees(int origin, int bound, int[] uniqueCoordinates, Map<Integer, List<Point>> uniqueCoordinatesMap) {
        super.buildSubTrees(origin, bound, uniqueCoordinates, uniqueCoordinatesMap);

        int newDimension = getDimension() - 1;
        subTree = newDimension == RangeTree2D.DIMENSION ? new RangeTree2D(points) : new RangeTreeKD(points, newDimension);
    }

    @Override
    protected int getRootLink(Rectangle rectangle) {
        return -1;
    }

    @Override
    protected int getLeftLink(int startLink) {
        return -1;
    }

    @Override
    protected int getRightLink(int startLink) {
        return -1;
    }
}
