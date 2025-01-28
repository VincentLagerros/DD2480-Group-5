package se.kth;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

public class EvaluateLIC {

    /**
     * Calculates if there exists three consecutive datapoints where the triangle formed from
     * using them as vertices has an area that exceeds the specified area threshold.
     *
     * @param coordinates   an array of the coordinates for the datapoints
     * @param areaThreshold the area which the triangle must exceed
     * @return true if the three datapoints exist, otherwise false
     */
    public boolean LIC3(Point2D[] coordinates, double areaThreshold) {
        if (areaThreshold < 0) return false;

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
     * Calculates if there is a cluster of consecutive datapoints that are in more than 
     * quadrantThreshold different quadrants. If a datapoint is between two quadrants
     * then priority is given to quadrant with lower number.
     * 
     * @param coordinates       an array of the coordinates for the datapoints
     * @param clusterSize       the size of the cluster of consecutive coordinates
     * @param quadsThreshold    the number of quadrants to be exceed
     * @return                  true if a cluster of coordinates are in more quadrants than quadsThreshold,
     *                          otherwise false
     */
    public boolean LIC4(Point2D[] coordinates, int clusterSize, int quadsThreshold){
        assert clusterSize >=2;
        assert coordinates.length >= clusterSize;
        assert quadsThreshold >=1;
        assert quadsThreshold <=3;
        if(clusterSize<=quadsThreshold) return false;

        ArrayList<Integer> quadList = new ArrayList<Integer>();

        for(Point2D coord:coordinates){
            if(coord.getX()>=0&&coord.getY()>=0){
                quadList.add(1);
            }else if(coord.getX()<0&&coord.getY()>=0){
                quadList.add(2);
            }else if(coord.getX()<=0){
                quadList.add(3);
            }else{
                quadList.add(4);
            }
        }

        HashSet<Integer> h = new HashSet<Integer>();

        for(int i=0;i<=coordinates.length-clusterSize;i++){
            h.addAll(quadList.subList(i, i+clusterSize));
            if(h.size()>quadsThreshold) return true;
            h.clear();
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
     * @param numPoints   Number of datapoints (3 ≤ nPts ≤ numPoints)
     * @param nPts        Number of consecutive points (3 ≤ nPts ≤ numPoints)
     * @param dist        distance computed to (0 ≤ DIST)
     * @return True if such a point exists, False otherwise
     */
    public boolean LIC6(Point2D[] coordinates, int numPoints, int nPts, double dist) {
        if (numPoints < 3 || nPts > numPoints || coordinates.length < 3) {
            return false;
        }

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
}
