package se.kth;

import java.awt.geom.Point2D;

public class EvaluateLIC {

    /**
     * There exists at least one set of two consecutive data points that are
     * a distance greater than the length, LENGTH1, apart.
     *
     * @param coordinates a Point2D array of the x- and y-coordinates for the datapoints
     * @param length1     length to check against
     * @return true if there exists two consecutive datapoints where the Euclidean
     * distance dist > length1 otherwise false
     */
    public static boolean LIC0(Point2D[] coordinates, double length1) {
        assert coordinates != null;
        assert length1 >= 0;

        if (coordinates.length < 2) {
            return false;
        }

        for (int i = 0; i < coordinates.length - 1; i++) {
            Point2D pt1 = coordinates[i];
            Point2D pt2 = coordinates[i + 1];
            double distance = pt1.distance(pt2);
            if (distance > length1) {
                return true;
            }
        }
        return false;
    }

    /**
     * There exists at least one set of three consecutive data points that
     * cannot all be contained within or on a circle of radius RADIUS1.
     *
     * @param coordinates a Point2D array of the x- and y-coordinates for the datapoints
     * @param radius1     radius to check against
     * @return true if the circumradius is larger than radius1, otherwise return false
     */
    public static boolean LIC1(Point2D[] coordinates, double radius1) {
        assert coordinates != null;
        assert radius1 >= 0;

        if (coordinates.length < 3) {
            return false;
        }

        for (int i = 0; i < coordinates.length - 2; i++) {
            Point2D pt1 = coordinates[i];
            Point2D pt2 = coordinates[i + 1];
            Point2D pt3 = coordinates[i + 2];

            // Calculate length of all sides in the triangle
            double a = pt1.distance(pt2);
            double b = pt2.distance(pt3);
            double c = pt3.distance(pt1);

            // Heron's formula
            double s = (a + b + c) / 2;
            double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));

            // If points are on a straight line.
            if (area == 0) {
                double largestDistance = Math.max(a, Math.max(b, c));

                if (largestDistance > radius1 * 2) {
                    return true; // The points cannot be contained in the circle
                }

                continue;
            }

            double circumradius = (a * b * c) / (4 * area);

            if (circumradius > radius1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates if there exists at least one set of three consecutive data points which form an angle such that:
     * angle < (PI−epsilon) or angle > (PI+epsilon)
     * The second of the three consecutive points is always the vertex of the angle. If either the first
     * point or the last point (or both) coincides with the vertex, the angle is undefined and the LIC is not satisfied by those three points.
     *
     * @param coordinates an array of the coordinates for the datapoints
     * @param epsilon     Fault tolerance of the angle (0 ≤ EPSILON < PI)
     * @return True if such an angle exists, False otherwise
     */
    public static boolean LIC2(Point2D[] coordinates, double epsilon) {
        assert epsilon >= 0;
        assert epsilon < Math.PI;
        int numPoints = coordinates.length;

        for (int i = 0; i < numPoints - 2; i++) {

            // Get the coordinates of three consecutive data points
            Point2D pt1 = coordinates[i];
            Point2D pt2 = coordinates[i + 1];
            Point2D pt3 = coordinates[i + 2];

            // Do not evaluate theese points if point 1 or 3  coincides with the vertex (point 2)
            if (pt1.equals(pt2) || pt3.equals(pt2)) {
                continue;
            }

            // Create diectional vectors with point 2 as vertex
            double xVector1 = pt2.getX() - pt1.getX();
            double yVector1 = pt2.getY() - pt1.getY();
            double xVector2 = pt2.getX() - pt3.getX();
            double yVector2 = pt2.getY() - pt3.getY();

            // Calculate the angle from the dot-product and magnitude of the vectors
            double dotProduct = (xVector1 * xVector2) + (yVector1 * yVector2);
            double magnitude1 = Math.sqrt(Math.pow(xVector1, 2) + Math.pow(yVector1, 2));
            double magnitude2 = Math.sqrt(Math.pow(xVector2, 2) + Math.pow(yVector2, 2));
            double angle = Math.acos(dotProduct / (magnitude1 * magnitude2));

            // Return True if LIC2 condition is met
            if (angle < (Math.PI - epsilon) || angle > (Math.PI + epsilon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates if there exists three consecutive datapoints where the triangle formed from
     * using them as vertices has an area that exceeds the specified area threshold.
     *
     * @param coordinates   an array of the coordinates for the datapoints
     * @param areaThreshold the area which the triangle must exceed
     * @return true if the three datapoints exist, otherwise false
     */
    public static boolean LIC3(Point2D[] coordinates, double areaThreshold) {
        assert coordinates != null;
        assert 0 <= areaThreshold;

        Point2D pt1, pt2, pt3;

        for (int i = 0; i < coordinates.length - 2; i++) {
            //get coordinates for the three datapoints
            pt1 = coordinates[i];
            pt2 = coordinates[i + 1];
            pt3 = coordinates[i + 2];

            if (getTriangleArea(pt1, pt2, pt3) > areaThreshold)
                return true;
        }
        return false;
    }

    /**
     * Calculates if there is a cluster of consecutive datapoints that are in more than
     * quadrantThreshold different quadrants. If a datapoint is between two quadrants
     * then priority is given to quadrant with lower number.
     *
     * @param coordinates    an array of the coordinates for the datapoints
     * @param clusterSize    the size of the cluster of consecutive coordinates
     * @param quadsThreshold the number of quadrants to be exceed
     * @return true if a cluster of coordinates are in more quadrants than quadsThreshold,
     * otherwise false
     */
    public static boolean LIC4(Point2D[] coordinates, int clusterSize, int quadsThreshold) {
        assert clusterSize >= 2;
        assert coordinates.length >= clusterSize;
        assert quadsThreshold >= 1;
        assert quadsThreshold <= 3;
        if (clusterSize <= quadsThreshold) return false;

        int[] quadrants = {0, 0, 0, 0};
        int sum = 0;

        // initialize window
        for (int i = 0; i < clusterSize; i++) {
            int add = getQuadrant(coordinates[i]);
            if (quadrants[add] == 0) sum += 1;
            quadrants[add] += 1;
            if (sum > quadsThreshold) return true;
        }

        // move window
        for (int i = 0; i < coordinates.length - clusterSize; i++) {
            int remove = getQuadrant(coordinates[i]);
            int add = getQuadrant(coordinates[i + clusterSize]);
            if (quadrants[add] == 0) sum += 1;
            if (quadrants[remove] == 1) sum -= 1;

            quadrants[remove] -= 1;
            quadrants[add] += 1;
            if (sum > quadsThreshold) return true;
        }
        return false;
    }

    /**
     * Get the Quadrant 0-3 mainly used for LIC4
     *
     * @param coord the datapoint
     * @return index of quadrant of datapoint, prioritizing lower index for datapoint on axis
     */
    public static int getQuadrant(Point2D coord) {
        if (coord.getX() >= 0 && coord.getY() >= 0) {
            return 0;
        } else if (coord.getX() < 0 && coord.getY() >= 0) {
            return 1;
        } else if (coord.getX() <= 0) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Calculates if there exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]),
     * such that X[j] - X[i] < 0. (where i = j-1)
     *
     * @param coordinates An array of the coordinates for the datapoints
     * @return True if such a set exists, False otherwise
     */
    public static boolean LIC5(Point2D[] coordinates) {
        int numPoints = coordinates.length;
        for (int j = 1; j < numPoints; j++) {
            if (coordinates[j].getX() - coordinates[j - 1].getX() < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates if there exists at least one set of nPts consecutive data points such that at least one of the
     * points lies a distance greater than dist from the line joining the first and last of these nPts points.
     * If the first and last points of these nPts are identical, then the calculated distance
     * to compare with dist will be the distance from the coincident point.
     * The condition is not met when NumPoints < 3.
     *
     * @param coordinates an array of the coordinates for the datapoints
     * @param nPts        Number of consecutive points (3 ≤ nPts ≤ coordinates.length)
     * @param dist        distance computed to (0 ≤ DIST)
     * @return True if such a point exists, False otherwise
     */
    public static boolean LIC6(Point2D[] coordinates, int nPts, double dist) {
        assert coordinates != null;
        assert 0 <= dist;
        assert 3 <= nPts;
        int numPoints = coordinates.length;
        assert nPts <= numPoints;

        // Iterate over all possible consecutive nPts large groups of points
        for (int i = 0; i <= numPoints - nPts; i++) {

            // Get the first and last points in the group
            Point2D pt1 = coordinates[i];
            Point2D ptN = coordinates[i + nPts - 1];

            // To handle the special case when the first and last points in the group are identical
            boolean identicalPoints = pt1.equals(ptN);

            for (int j = i + 1; j < i + nPts - 1; j++) {
                Point2D ptCurrent = coordinates[j];

                double distance;

                // special case 
                if (identicalPoints) {
                    distance = ptCurrent.distance(pt1);
                }
                // Default case 
                else {
                    // line on the form of ax+by+c=0 using (y1 – y2)x + (x2 – x1)y + (x1y2 – x2y1) = 0
                    double a = pt1.getY() - ptN.getY();
                    double b = ptN.getX() - pt1.getX();
                    double c = (pt1.getX() * ptN.getY()) - (ptN.getX() * pt1.getY());

                    distance = Math.abs(a * ptCurrent.getX() + b * ptCurrent.getY() + c) / pt1.distance(ptN);
                }

                if (distance > dist) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Calculates if there exists at least one set of two data points separated by exactly kPts consecutive
     * intervening points that are a distance greater than the length, lenght1, apart.
     * The conditions are not met when numPoints < 3.
     *
     * @param coordinates an array of the coordinates for the datapoints
     * @param kPts        Number of consecutive intervening datapoints sperating the points compared (1 ≤ kPts ≤ (numPoints - 2)
     * @param length1     Length of minimum distance between the two separated points
     * @return True if such a point exists, False otherwise
     */
    public static boolean LIC7(Point2D[] coordinates, int kPts, double length1) {
        int numPoints = coordinates.length;
        if (numPoints < 3) {
            return false;
        }

        assert kPts >= 1;
        assert kPts <= numPoints - 2;

        for (int i = 0; i < numPoints - kPts - 1; i++) {
            double distance = coordinates[i].distance(coordinates[i + kPts + 1]);
            if (distance > length1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates if there exists at least one set of three data points separated by exactly A_PTS and B_PTS
     * consecutive intervening points, respectively, that cannot be contained within or on a circle of radius
     * RADIUS1. The condition is not met when NUMPOINTS < 5.
     *
     * @param coordinates an array of the coordinates for the datapoints
     * @param aPts        Number of consecutive intervening datapoints sperating the points to the left
     * @param bPts        Number of consecutive intervening datapoints sperating the points to the right
     * @return True if such points exist
     */
    public static boolean LIC8(Point2D[] coordinates, int aPts, int bPts, double radius) {
        assert coordinates != null;
        int numPoints = coordinates.length;

        if (numPoints < 5) {
            return false;
        }

        assert aPts >= 1;
        assert bPts >= 1;
        assert aPts + bPts <= numPoints - 3;

        for (int i = 0; i + aPts + bPts + 2 < numPoints; i++) {
            Point2D pt1 = coordinates[i];
            Point2D pt2 = coordinates[i + aPts + 1];
            Point2D pt3 = coordinates[i + aPts + bPts + 2];
            double ax = pt1.getX(), ay = pt1.getY();
            double bx = pt2.getX(), by = pt2.getY();
            double cx = pt3.getX(), cy = pt3.getY();
            double a = pt1.distance(pt2);
            double b = pt2.distance(pt3);
            double c = pt3.distance(pt1);

            // https://en.wikipedia.org/wiki/Circumcircle 
            double d = 2 * (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by));
            // If the points are collinear
            if (d == 0) {
                double largestDistance = Math.max(a, Math.max(b, c));

                if (largestDistance > radius * 2) {
                    return true; // The points cannot be contained in the circle
                }

                continue;
            }
            //Coordinates for circumcenter
            double ux = ((ax * ax + ay * ay) * (by - cy) +
                    (bx * bx + by * by) * (cy - ay) +
                    (cx * cx + cy * cy) * (ay - by)) / d;
            double uy = ((ax * ax + ay * ay) * (cx - bx) +
                    (bx * bx + by * by) * (ax - cx) +
                    (cx * cx + cy * cy) * (bx - ax)) / d;

            double circumradius = pt1.distance(ux, uy); // Circumradius is distance from circumcenter to any point
            if (circumradius > radius) {
                return true;
            }
        }
        return false;
    }

    /**
     * There exists at least one set of three data points separated by exactly C_PTS and D_PTS
     * consecutive intervening points, respectively, that form an angle such that:
     * angle < (PI − EPSILON)
     * or
     * angle > (PI + EPSILON)
     *
     * @param coordinates an array of the coordinates for the datapoints
     * @param cPts        Offset to the left of the vertex in the coordinates array
     * @param dPts        Offset to the right of the vertex
     * @param epsilon     How far away in radians from PI (180 deg) these three points can be to not form a straight line
     * @return If three data points can be found following the conditions
     */
    public static boolean LIC9(Point2D[] coordinates, int cPts, int dPts, double epsilon) {
        assert coordinates != null;
        int numPoints = coordinates.length;
        assert cPts >= 1;
        assert dPts >= 1;
        assert cPts + dPts <= numPoints - 3;
        if (numPoints < 5) {
            return false;
        }
        for (int i = cPts + 1; i < numPoints - dPts - 1; i++) {
            Point2D pt1 = coordinates[i]; // vertex
            Point2D pt2 = coordinates[i - cPts - 1]; // left
            Point2D pt3 = coordinates[i + dPts + 1]; // right

            // If either the first point or the last point (or both)
            // coincide with the vertex, the angle is undefined and the LIC
            // is not satisfied by those three points
            if (pt1.equals(pt2) || pt1.equals(pt3)) {
                continue;
            }

            // https://stackoverflow.com/questions/1211212/how-to-calculate-an-angle-from-three-points
            double angle = Math.acos((pt1.distanceSq(pt2) + pt1.distanceSq(pt3) - pt2.distanceSq(pt3))
                    / (2.0 * pt1.distance(pt2) * pt1.distance(pt3)));

            if (angle < Math.PI - epsilon || angle > Math.PI + epsilon) {
                return true;
            }
        }
        return false;
    }

    /**
     * There exists at least one set of three data points separated by exactly E PTS and F PTS
     * consecutive intervening points, respectively, that are the vertices of a triangle with
     * area greater than AREA1. The condition is not met when NUMPOINTS < 5.
     *
     * @param coordinates an array of the coordinates for the datapoints
     * @param ePts        number of consecutive points between the first and second selected point, 1 ≤ E PTS, E PTS+F PTS ≤ NUMPOINTS−3
     * @param fPts        represents the number of consecutive points between the second and third selected point, 1 ≤ F PTS, E PTS+F PTS ≤ NUMPOINTS−3
     * @param area1       area used to compare with
     * @return If three data points can be found following the conditions
     */
    public static boolean LIC10(Point2D[] coordinates, int ePts, int fPts, double area1) {
        assert coordinates != null;
        int numPoints = coordinates.length;
        if (numPoints < 5) {
            return false;
        }

        assert ePts >= 1;
        assert fPts >= 1;
        assert ePts + fPts <= numPoints - 3;

        for (int i = 0; i < numPoints - ePts - fPts - 2; i++) {
            Point2D pt1 = coordinates[i]; //0
            Point2D pt2 = coordinates[i + ePts + 1]; // 3
            Point2D pt3 = coordinates[i + ePts + fPts + 2]; // 5

            double area = getTriangleArea(pt1, pt2, pt3);
            // LIC condition
            if (area > area1) {
                return true;
            }
        }
        return false;
    }

    /**
     * There exists at least one set of two data points, (X[i],Y[i]) and (X[j],Y[j]), separated by
     * exactly gPts consecutive intervening points, such that X[j] - X[i] < 0. (where i < j ) The
     * condition is not met when NUMPOINTS < 3.
     *
     * @param coordinates An array of the coordinates for the datapoints
     * @param gPts        Number of point seperating the evaluated points
     * @return True if such a set exists, False otherwise
     */
    public static boolean LIC11(Point2D[] coordinates, int gPts) {
        int numPoints = coordinates.length;

        // Condition is not met when NUMPOINTS < 3
        if (numPoints < 3) {
            return false;
        }
        // Validating input
        // This must be done after numPoints is checked due to edge Case, if numPoints = 3 then gPts = 2 is invalid,
        // rather than an assertion error.
        assert gPts >= 1;
        assert gPts <= numPoints - 2;


        for (int i = 0; i < numPoints - gPts - 1; i++) {
            // LIC condition
            if (coordinates[i + gPts + 1].getX() - coordinates[i].getX() < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * There exists at least one set of two data points, separated by exactly K PTS consecutive
     * intervening points, which are a distance greater than the length, LENGTH1, apart. In addition,
     * there exists at least one set of two data points (which can be the same or different from
     * the two data points just mentioned), separated by exactly K PTS consecutive intervening
     * points, that are a distance less than the length, LENGTH2, apart. Both parts must be true
     * for the LIC to be true. The condition is not met when coordinates.length < 3.
     *
     * @param coordinates an array of the coordinates for the datapoints
     * @param kPts        number of consecutive intervening points
     * @param length2     length used to compare the distance of the points, 0 ≤ LENGTH2
     * @return If three data points can be found following the conditions
     */
    public static boolean LIC12(Point2D[] coordinates, int kPts, double length1, double length2) {
        if (coordinates.length < 3) {
            return false;
        }
        assert length2 >= 0;
        assert kPts >= 0;


        boolean greater = false;
        boolean smaller = false;

        for (int i = 0; i < coordinates.length - kPts - 1; i++) {
            Point2D pt1 = coordinates[i];
            Point2D pt2 = coordinates[i + kPts + 1];

            double distance = pt1.distance(pt2);
            greater |= distance > length1;
            smaller |= distance < length2;
        }

        return greater && smaller;
    }

    /**
     * Calculates if here exists at least one set of three data points, separated by exactly aPts and bPts
     * consecutive intervening points, respectively, that cannot be contained within or on a circle of
     * radius RADIUS1. In addition, there exists at least one set of three data points (which can be
     * the same or different from the three data points just mentioned) separated by exactly A PTS
     * and B PTS consecutive intervening points, respectively, that can be contained in or on a
     * circle of radius RADIUS2.
     *
     * @param coordinates an array of the coordinates for the datapoints
     * @param aPts        left offset of the center vertex in coordinates
     * @param bPts        right offset of the center vertex in coordinates
     * @param radius1     the radius of the circle that cannot contain the three points
     * @param radius2     the radius of the circle that can contain the three points
     * @return true if three points separated by aPts and bPts cannot be contained in circle with radius1 and three points
     * separated by aPts and bPts can be contained in circle with radius2, otherwise false
     */
    public static boolean LIC13(Point2D[] coordinates, int aPts, int bPts, double radius1, double radius2) {
        assert radius2 >= 0;
        if (coordinates.length < 5) return false;

        boolean condition1 = false;
        boolean condition2 = false;


        for (int i = aPts + 1; i < coordinates.length - bPts - 1; i++) {
            Point2D pt1 = coordinates[i - aPts - 1];
            Point2D pt2 = coordinates[i];
            Point2D pt3 = coordinates[i + bPts + 1];

            // get distances between points
            double a = pt1.distance(pt2);
            double b = pt2.distance(pt3);
            double c = pt3.distance(pt1);

            Point2D[] points = new Point2D[3];

            // check for greatest distance
            if (a >= b && a >= c) {
                points[0] = pt1;
                points[1] = pt2;
                points[2] = pt3;
            } else if (b >= a && b >= c) {
                points[0] = pt2;
                points[1] = pt3;
                points[2] = pt1;
            } else {
                points[0] = pt1;
                points[1] = pt3;
                points[2] = pt2;
            }

            // calculate midpoint between the two points that are furthest apart
            Point2D mid = new Point2D.Double(0.5 * (points[0].getX() + points[1].getX()), 0.5 * (points[0].getY() + points[1].getY()));
            double minRadiusA = points[0].distance(mid);

            // check if third point is within the cicle spanning between the other two
            if (mid.distance(points[2]) <= minRadiusA) {
                if (minRadiusA > radius1) {
                    condition1 = true;
                }
                if (minRadiusA <= radius2) {
                    condition2 = true;
                }
            }
            if (condition1 && condition2) return true;

            // calculate triangle area between points
            double k = getTriangleArea(pt1, pt2, pt3);

            // calculate area of circle with all points on its edge
            double minRadiusB = (a * b * c) / (4 * k);

            if (minRadiusB > radius1) {
                condition1 = true;
            }
            if (minRadiusB <= radius2) {
                condition2 = true;
            }
        }
        return condition1 && condition2;
    }

    /**
     * Gets the area of the triangle created by the 3 points
     *
     * @param pt1 Point 1
     * @param pt2 Point 2
     * @param pt3 Point 3
     * @return The area
     */
    public static double getTriangleArea(Point2D pt1, Point2D pt2, Point2D pt3) {
        // https://www.cuemath.com/geometry/area-of-triangle-in-coordinate-geometry/
        return (0.5) * Math.abs(
                pt1.getX() * (pt2.getY() - pt3.getY()) +
                        pt2.getX() * (pt3.getY() - pt1.getY()) +
                        pt3.getX() * (pt1.getY() - pt2.getY()));
    }

    /**
     * There exists at least one set of three data points, separated by exactly E PTS and F PTS con-
     * secutive intervening points, respectively, that are the vertices of a triangle with area greater
     * than AREA1. In addition, there exist three data points (which can be the same or different
     * from the three data points just mentioned) separated by exactly E PTS and F PTS consec-
     * utive intervening points, respectively, that are the vertices of a triangle with area less than
     * AREA2. Both parts must be true for the LIC to be true. The condition is not met when
     * NUMPOINTS < 5.
     *
     * @param coordinates An array of the coordinates for the datapoints
     * @param ePts        Left offset of the center vertex in coordinates
     * @param fPts        Right offset of the center vertex in coordinates
     * @param area1       The area that a triangle should be greater than
     * @param area2       The area that a triangle should be less than
     * @return If there is one triangle that can be formed that has and area greater than area1 and one less than area2
     * from coordinates separated by ePts and fPts
     */
    public static boolean LIC14(Point2D[] coordinates, int ePts, int fPts, double area1, double area2) {
        assert coordinates != null;
        assert 0 <= area2;

        int numPoints = coordinates.length;
        if (numPoints < 5) {
            return false;
        }

        boolean greater = false;
        boolean less = false;
        for (int i = 0; i < numPoints - ePts - fPts - 2; i++) {
            Point2D pt1 = coordinates[i];
            Point2D pt2 = coordinates[i + ePts + 1];
            Point2D pt3 = coordinates[i + ePts + fPts + 2];
            double area = getTriangleArea(pt1, pt2, pt3);
            greater |= area > area1;
            less |= area < area2;
        }
        return greater && less;
    }
}
