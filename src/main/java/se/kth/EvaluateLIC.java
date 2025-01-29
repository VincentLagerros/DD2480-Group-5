package se.kth;

import java.awt.geom.Point2D;

public class EvaluateLIC {

    /**
     * 
     * There exists at least one set of two consecutive data points that are 
     * a distance greater than the length, LENGTH1, apart.
     * 
     * @param xCoordinates An array of the x-coordinates for the datapoints
     * @param yCoordinates An array of the y-coordinates for the datapoints
     * @param numPoints Number of datapoints
     * @param length1 length to check against
     * @return true if there exists two consecutive datapoints where the Euclidean 
     *         distance dist > length1 otherwise false
     * 
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
     * Calculates if there exists at least one set of three consecutive data points which form an angle such that:
     * angle < (PI−epsilon) or angle > (PI+epsilon)
     * The second of the three consecutive points is always the vertex of the angle. If either the first
     * point or the last point (or both) coincides with the vertex, the angle is undefined and the LIC is not satisfied by those three points.
     *
     * @param coordinates   an array of the coordinates for the datapoints 
     * @param epsilon       Fault tolerance of the angle (0 ≤ EPSILON < PI)
     * @return              True if such an angle exists, False otherwise
     */
    public boolean LIC2(Point2D[] coordinates, double epsilon){
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

            // Calculate the angle from the dot-product and and magnitude of the vectors
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
    public boolean LIC3(Point2D[] coordinates, double areaThreshold) {
        assert coordinates != null;
        assert 0 <= areaThreshold;

        Point2D pt1, pt2, pt3;

        for (int i = 0; i < coordinates.length - 2; i++) {
            //get coordinates for the three datapoints
            pt1 = coordinates[i];
            pt2 = coordinates[i + 1];
            pt3 = coordinates[i + 2];

            if (0.5 * (Math.abs(pt1.getX() * pt2.getY() + pt2.getX() * pt3.getY() + pt3.getX() * pt1.getY() - pt1.getX() * pt3.getY() - pt2.getX() * pt1.getY() - pt3.getX() * pt2.getY())) > areaThreshold)
                return true;
            
            
        }
        return false;
    }

    /**
     * Calculates if there exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), 
     * suchthat X[j] - X[i] < 0. (where i = j-1)
     *
     * @param coordinates   An array of the coordinates for the datapoints
     * @return              True if such a set exists, False otherwise
     */
    public boolean LIC5(Point2D[] coordinates){
        int numPoints = coordinates.length;
        for(int j = 1; j < numPoints; j++){
            if(coordinates[j].getX() - coordinates[j-1].getX() < 0){
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
    public boolean LIC6(Point2D[] coordinates, int nPts, double dist) {
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
     * The conditionis not met when numPoints < 3.
     *
     * @param coordinates   an array of the coordinates for the datapoints 
     * @param kPts          Number of consecutive intervening datapoints sperating the points compared (1 ≤ kPts ≤ (numPoints - 2)
     * @param lenght1       Length of minimum distance between the two separated points
     * @return              True if such a point exists, False otherwise
     */
    public boolean LIC7(Point2D[] coordinates, int kPts, double length1){
        int numPoints = coordinates.length;
        if(numPoints < 3){
            return false;
        }
        
        assert kPts >= 1;
        assert kPts <= numPoints - 2;

        for(int i = 0; i < numPoints - kPts -1; i++){
            double distance = coordinates[i].distance(coordinates[i+kPts+1]);
            if(distance > length1){
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
    public boolean LIC9(Point2D[] coordinates, int cPts, int dPts, double epsilon) {
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
     * @return            If three data points can be found following the conditions
     */
    public boolean LIC10(Point2D[] coordinates, int ePts, int fPts, double area1) {
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
            
            // https://www.cuemath.com/geometry/area-of-triangle-in-coordinate-geometry/
            double area = (0.5) * Math.abs(
                                            pt1.getX() * (pt2.getY() - pt3.getY()) +
                                            pt2.getX() * (pt3.getY() - pt1.getY()) + 
                                            pt3.getX() * (pt1.getY() - pt2.getY()));
            // LIC condition
            if( area > area1){
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
    * @param coordinates   An array of the coordinates for the datapoints
    * @param gPts          Number of point seperating the evaluated points
    * @return              True if such a set exists, False otherwise
    */
   public boolean LIC11(Point2D[] coordinates, int gPts){
       int numPoints = coordinates.length;

       // Condition is not met when NUMPOINTS < 3
       if(numPoints < 3){
           return false;
       }        
       // Validating input
       // This must be done after numPoints is checked due to edge Case, if numPoints = 3 then gPts = 2 is invalid, 
       // rather than an assertion error. 
       assert gPts >= 1;
       assert gPts <= numPoints - 2;


       for(int i = 0; i < numPoints - gPts - 1; i++){
           // LIC condition
           if(coordinates[i + gPts + 1].getX() - coordinates[i].getX() < 0){
               return true;
           }
       }
      return false;
   }
}
