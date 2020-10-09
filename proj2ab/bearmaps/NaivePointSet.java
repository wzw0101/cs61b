package bearmaps;

import java.security.cert.PolicyNode;
import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> points;

    // You can assume points has at least size 1.
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point qPoint = new Point(x, y);
        Point nPoint = null;
        double nDistance = Double.POSITIVE_INFINITY;
        for (Point point : points) {
            double distance = Point.distance(qPoint, point);
            if (nDistance > distance) {
                nDistance = distance;
                nPoint = point;
            }
        }
        return nPoint;
    }

}
