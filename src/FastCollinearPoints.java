import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;

    private static Point[] deduplication(Point[] points) {
        //deduplication
        Point[] newPoints = new Point[points.length];
        int cnt = 0;
        Arrays.sort(points, Point::compareTo);
        int z = 0;
        for (int i = 0; i < points.length - 1; i++) {
            if (!points[i].toString().equals(points[i + 1].toString())) {
                newPoints[z++] = points[i];
                ++cnt;

            }
        }

        if (points[points.length - 1] != points[points.length - 2]) newPoints[cnt++] = points[points.length - 1];
        points = new Point[cnt];
        System.arraycopy(newPoints, 0, points, 0, cnt);

        return points;

        //end of deduplication
    }

    public FastCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) throw new IllegalArgumentException();

        points = deduplication(points);

        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        Point[] newPoints = new Point[points.length];
        System.arraycopy(points, 0, newPoints, 0, points.length);

        for (Point point : points) {

            Arrays.sort(newPoints, Point::compareTo);
            Arrays.sort(newPoints, point.slopeOrder());

//            for (Point value : newPoints) {
//                System.out.println(value.toString() + "Slope " + point.slopeTo(value));
//            }
//            System.out.println();

            double curSlope = point.slopeTo(newPoints[1]);
            int pointsInRow = 1;
            for (int i = 2; i < newPoints.length; i++) {
                if (Double.compare(curSlope, point.slopeTo(newPoints[i])) != 0) {
                    if (pointsInRow >= 4) {
                        Point minPoint = point, maxPoint = point;
                        if (newPoints[i - pointsInRow + 1].compareTo(minPoint) < 0)
                            minPoint = newPoints[i - pointsInRow + 1];
                        if (newPoints[i - 1].compareTo(maxPoint) > 0) maxPoint = newPoints[i - 1];

                        if (maxPoint.compareTo(minPoint) < 0) {
                            minPoint = point;
                            maxPoint = point;
                            if (newPoints[i - pointsInRow + 1].compareTo(maxPoint) < 0)
                                maxPoint = newPoints[i - pointsInRow + 1];
                            if (newPoints[i - 1].compareTo(minPoint) > 0) minPoint = newPoints[i - 1];
                        }

                        lineSegmentArrayList.add(new LineSegment(minPoint, maxPoint));
                    }
                    pointsInRow = 2;
                    curSlope = point.slopeTo(newPoints[i]);
                } else {
                    ++pointsInRow;

                }
            }
            if (pointsInRow >= 4) {
                Point minPoint = point, maxPoint = point;
                if (newPoints[points.length - pointsInRow + 1].compareTo(minPoint) < 0)
                    minPoint = newPoints[points.length - pointsInRow + 1];
                if (newPoints[points.length - 1].compareTo(maxPoint) > 0) maxPoint = newPoints[points.length - 1];

                if (maxPoint.compareTo(minPoint) < 0) {
                    minPoint = point;
                    maxPoint = point;
                    if (newPoints[points.length - pointsInRow + 1].compareTo(maxPoint) < 0)
                        maxPoint = newPoints[points.length - pointsInRow + 1];
                    if (newPoints[points.length - 1].compareTo(minPoint) > 0) minPoint = newPoints[points.length - 1];
                }

                lineSegmentArrayList.add(new LineSegment(minPoint, maxPoint));
            }

        }


        lineSegments = new LineSegment[lineSegmentArrayList.size()];
        for (int i = 0; i < lineSegments.length; i++) {
            lineSegments[i] = lineSegmentArrayList.get(i);
        }
        //System.out.println(Arrays.toString(lineSegments));

        int newSize = 0;

        for (int i = 0; i < lineSegments.length - 1; i++) {
            if(lineSegments[i] != null) {
                ++newSize;
                for (int j = i + 1; j < lineSegments.length; j++) {
                    if (lineSegments[j] == null) continue;
                    if (lineSegments[j].toString().equals(lineSegments[i].toString())) lineSegments[j] = null;
                }
            }
        }

        //System.out.println(Arrays.toString(lineSegments));
        LineSegment[] newLineSegments = new LineSegment[newSize];
        int j = 0;
        for (LineSegment lineSegment : lineSegments) {

            if (lineSegment != null)
                newLineSegments[j++] = lineSegment;
        }
        lineSegments = newLineSegments;

    }

    public int numberOfSegments()        // the number of line segments
    {
        return lineSegments.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        return lineSegments;
    }
}
