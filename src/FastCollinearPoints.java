import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;


    public FastCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) throw new IllegalArgumentException();
        if (points.length < 4) {
            lineSegments = new LineSegment[0];
            return;
        }
        Arrays.sort(points, Point::compareTo);
        //перечитать про выбрасываемые исключения
        //один элемент в конце лишний походу
        deduplication(points);

        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        Point[] newPoints = new Point[points.length];
        System.arraycopy(points, 0, newPoints, 0, points.length);

        for (Point point : points) {

            Arrays.sort(newPoints, Point::compareTo);
            Arrays.sort(newPoints, point.slopeOrder());


            double curSlope = point.slopeTo(newPoints[1]);
            int pointsInRow = 2;
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


        LineSegment[] newLineSegments = new LineSegment[lineSegmentArrayList.size()];
        for (int i = 0; i < newLineSegments.length; i++) {
            newLineSegments[i] = lineSegmentArrayList.get(i);
        }
        //System.out.println(Arrays.toString(lineSegments));

        int newSize = 0;

        for (int i = 0; i < newLineSegments.length - 1; i++) {
            if (newLineSegments[i] != null) {
                ++newSize;
                for (int j = i + 1; j < newLineSegments.length; j++) {
                    if (newLineSegments[j] == null) continue;
                    if (newLineSegments[j].toString().equals(newLineSegments[i].toString())) newLineSegments[j] = null;
                }
            }
        }

        //System.out.println(Arrays.toString(lineSegments));
        lineSegments = new LineSegment[newSize];
        int j = 0;
        for (LineSegment lineSegment : newLineSegments) {

            if (lineSegment != null)
                lineSegments[j++] = lineSegment;
        }


    }

    private static void deduplication(Point[] points) {

        if (points[0] == null) throw new IllegalArgumentException();
        for (int i = 1; i < points.length; i++) {
            if (points[i] == null || points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
        }


        //end of deduplication
    }

    public int numberOfSegments()        // the number of line segments
    {
        return lineSegments.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        return lineSegments.clone();
    }
}
