package ru.spbau.ads.kozlov.rangeQuries;

import ru.spbau.ads.kozlov.rangeQuries.coordinates.ICoordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author adkozlov
 */
public class RangeTreeKD<T extends Comparable<T>, C extends ICoordinate<T>> extends AbstractRangeTree<T, C> {

    private AbstractRangeTree<T, C> subTree;

    protected RangeTreeKD(int dimension) {
        super(dimension);
    }

    protected RangeTreeKD(int dimension, List<Point<T, C>> points) {
        super(dimension, points);
    }

    @Override
    protected void buildTree(int origin, int bound, List<C> uniqueCoordinates,
                             Map<C, ArrayList<Point<T, C>>> uniqueCoordinatesMap) {
        buildSubTrees(origin, bound, uniqueCoordinates, uniqueCoordinatesMap);

        List<Point<T, C>> points = getPoints();
        int dimension = getDimension() - 1;
        subTree = dimension == RangeTree2D.DIMENSION ? new RangeTree2D<>(points) : new RangeTreeKD<>(dimension, points);
    }

    @Override
    protected RangeTreeKD<T, C> createSubTree() {
        return new RangeTreeKD<>(getDimension());
    }

    @Override
    protected ArrayList<Point<T, C>> innerQuery(int origin, int bound, Rectangle<T, C> rectangle, int startLink) {
        return subTree.query(rectangle);
    }
}
