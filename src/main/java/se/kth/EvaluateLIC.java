package se.kth;

public class EvaluateLIC {

    /**
     * Calculates if there exists at least one set of nPts consecutive data points such that at least one of the
     * points. If the first and last points of these nPts are identical, then the calculated distance 
     * to compare with dist will be the distance from the coincident point. The condition is not met when NumPoints < 3. 
     *
     * @param xCoordinates  An array of the x-coordinates for the datapoints 
     * @param yCoordinates  An array of the y-coordinates for the datapoints
     * @param numPoints     Number of datapoints (3 ≤ nPts ≤ numPoints)
     * @param nPts          Number of consequative points (3 ≤ nPts ≤ numPoints)
     * @param dist          distance compated to (0 ≤ DIST)
     * @return              True if such a point exists, False otherwise
     */
    public boolean LIC6(double[] xCoordinates, double[] yCoordinates, int numPoints, int nPts, double dist){
        if(numPoints < 3 || nPts > numPoints || xCoordinates.length < 3){
            return false;
        }

        // Iterate over all possible consecutive nPts large groups of points
        for(int i = 0; i <= numPoints - nPts; i++){

            // Get the first and last points in the group
            double x1 = xCoordinates[i];
            double y1 = yCoordinates[i];
            double xN = xCoordinates[i + nPts - 1];
            double yN = yCoordinates[i + nPts - 1];

            // To handle the special case when the first and last points in the grouo are identical 
            boolean identicalPoints = (x1 == xN && y1 == yN);

            for (int j = i + 1; j < i + nPts - 1; j++) {
                double xCurrent = xCoordinates[j];
                double yCurrent = yCoordinates[j];

                double distance;

                // special case 
                if(identicalPoints){
                    distance = Math.sqrt(Math.pow((xCurrent - x1), 2) + Math.pow((yCurrent - y1), 2));
                }
                // Default case 
                else{
                    // line on the form of ax+by+c=0 using (y1 – y2)x + (x2 – x1)y + (x1y2 – x2y1) = 0
                    double a = y1 - yN;
                    double b = xN - x1;
                    double c = (x1 * yN) - (xN * y1);

                    distance = Math.abs(a*xCurrent + b*yCurrent +c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
                }

                if(distance > dist){
                    return true;
                }
            }
        }
        return false;
    }
}
