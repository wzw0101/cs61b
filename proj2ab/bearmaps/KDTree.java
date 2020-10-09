package bearmaps;

import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {

    private KDTreeNode root = null;

    private Comparator<KDTreeNode> nodeComparator = (pNode, qNode) ->{
        if (pNode.depth % 2 == 0) {
            return Double.compare(pNode.point.getX(), qNode.point.getX());
        } else {
            return Double.compare(pNode.point.getY(), qNode.point.getY());
        }
    };

    //You can assume points has at least size 1.
    public KDTree(List<Point> points) {
        points.forEach(point -> insertNode(point));
    }

    private void insertNode(Point point) {
        if (root == null) root = new KDTreeNode(point, 0);
        boolean stop = false;
        KDTreeNode pNode = root;
        KDTreeNode qNode = new KDTreeNode(point, -1);
        while (!stop) {
            if (pNode.point.equals(qNode.point)) {
                stop = true;
            } else if (nodeComparator.compare(pNode, qNode) > 0) {
                if (pNode.smaller == null) {
                    qNode.depth = pNode.depth + 1;
                    pNode.smaller = qNode;
                    stop = true;
                } else {
                    pNode = pNode.smaller;
                }
            } else {
                if (pNode.greater == null) {
                    qNode.depth = pNode.depth + 1;
                    pNode.greater = qNode;
                    stop = true;
                } else {
                    pNode = pNode.greater;
                }
            }
        }

    }

    @Override
    public Point nearest(double x, double y) {
        if (root == null) return null;
        KDTreeNode qNode = new KDTreeNode(new Point(x, y), -1);
        return nearestHelper(root, qNode, null);
    }

    private Point nearestHelper(KDTreeNode pNode, KDTreeNode qNode, Point np) {
        if (pNode == null) return np;

        if (np == null) {
            np = pNode.point;
        } else if (Point.distance(np, qNode.point) > Point.distance(pNode.point, qNode.point)) {
            np = pNode.point;
        }

        if (pNode.point.equals(qNode.point)) {
            return pNode.point;
        } else if (nodeComparator.compare(pNode, qNode) > 0) { // smaller side is the good side.
            np = nearestHelper(pNode.smaller, qNode, np);
            double nd = Point.distance(np, qNode.point);
            if (pNode.depth % 2 == 0) {
                if (nd > Math.abs(qNode.point.getX() - pNode.point.getX())){
                    np = nearestHelper(pNode.greater, qNode, np);
                }
            } else {
                if (nd > Math.abs(qNode.point.getY() - pNode.point.getY())){
                    np = nearestHelper(pNode.greater, qNode, np);
                }
            }
        } else { // greater side is the good side
            np = nearestHelper(pNode.greater, qNode, np);
            double nd = Point.distance(np, qNode.point);
            if (pNode.depth % 2 == 0) {
                if (nd > Math.abs(qNode.point.getX() - pNode.point.getX())){
                    np = nearestHelper(pNode.smaller, qNode, np);
                }
            } else {
                if (nd > Math.abs(qNode.point.getY() - pNode.point.getY())){
                    np = nearestHelper(pNode.smaller, qNode, np);
                }
            }
        }
        return np;
    }
}

class KDTreeNode {
    Point point;
    KDTreeNode greater;
    KDTreeNode smaller;
    int depth;

    public KDTreeNode(Point point, int depth) {
        this.point = point;
        this.depth = depth;
    }
}