import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;


    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {

        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();

        if (points == null) throw new IllegalArgumentException();
        Arrays.sort(points, Point::compareTo);
        Point cur = points[0];
        Point prev = null;
        for (Point point : points) {
            cur = point;

            if(cur == prev)
                continue;

            int i = 0;


            while (point.slopeTo(points[i]) == Double.NEGATIVE_INFINITY) ++i;
            System.out.println(Arrays.toString(points));
            int cnt = 1;
            int curIdx = i + 1;
            double curSlope = point.slopeTo(points[i]);

            for (i = curIdx + 1; i < points.length; i++) {

                if (point.slopeTo(points[i]) == curSlope) ++cnt;
                else {
                    if (cnt >= 4) {
                        lineSegmentArrayList.add(new LineSegment(point, points[i - 1]));

                    }
                    curIdx = i;
                    cnt = 1;
                    curSlope = point.slopeTo(points[i]);
                }

            }
            ++cnt;
            if (cnt >= 4) {
                lineSegmentArrayList.add(new LineSegment(points[curIdx], points[i - 1]));

            }
            prev = cur;

        }

        lineSegments = new LineSegment[lineSegmentArrayList.size()];
        for (int i = 0; i < lineSegments.length; ++i) {
            lineSegments[i] = lineSegmentArrayList.get(i);
        }


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
