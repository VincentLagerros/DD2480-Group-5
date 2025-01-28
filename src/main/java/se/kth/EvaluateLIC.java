package se.kth;

public class EvaluateLIC {
    
    /**
     * Calculates if there exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), 
     * suchthat X[j] - X[i] < 0. (where i = j-1)
     *
     * @param xCoordinates  An array of the x-coordinates for the datapoints 
     * @return              True if such a set exists, False otherwise
     */
    public boolean LIC5(double[] xCoordinates){
        for(int j = 1; j < xCoordinates.length; j++){
            if(xCoordinates[j] - xCoordinates[j-1] < 0){
                return true;
            }
        }
       return false;
    }
}
