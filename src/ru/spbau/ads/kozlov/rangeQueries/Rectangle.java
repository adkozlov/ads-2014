package ru.spbau.ads.kozlov.rangeQueries;

/**
 * @author adkozlov
 */
public class Rectangle implements IHasDimension {

    private final Point bottom, top;

    public Rectangle(Point bottom, Point top) {
        this.bottom = bottom;
        this.top = top;
    }

    @Override
    public int getDimension() {
        return bottom.getDimension();
    }

    public Point getBottom() {
        return bottom;
    }

    public Point getTop() {
        return top;
    }

    public boolean contains(Point point) {
        boolean result = true;
        for (int i = 0; i < getDimension(); i++) {
            int coordinate = point.getCoordinate(i);

            if (bottom.getCoordinate(i) > coordinate || top.getCoordinate(i) < coordinate) {
                result = false;
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectangle)) return false;

        Rectangle rectangle = (Rectangle) o;

        return bottom.equals(rectangle.bottom) && top.equals(rectangle.top);
    }

    @Override
    public int hashCode() {
        int result = bottom.hashCode();
        result = 31 * result + top.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "bottom=" + bottom +
                ", top=" + top +
                '}';
    }
}
