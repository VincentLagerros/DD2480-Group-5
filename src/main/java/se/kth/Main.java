package se.kth;

public class Main {
    public static void main(String[] args) {
        System.out.print("Hello world");
    }

    /**
     * Calculates if there exists three consecutive datapoints where the triangle formed from 
     * using them as vertecies has an area that exceeds the specified area threshold. 
     *
     * @param xCoordinates  an array of the x-coordinates for the datapoints 
     * @param yCoordinates  an array of the y-coordinates for the datapoints
     * @param areaThreshold the area which the triangle must exceed
     * @return              true if the three datapoints exist, otherwise false
     */
    public boolean LIC3(double[] xCoordinates, double[] yCoordinates, double areaThreshold){
        if(areaThreshold<0) return false;

        double x1, x2, x3, y1, y2, y3;

        for(int i=0;i<xCoordinates.length-2;i++){
            //get coordinates for the three datapoints
            x1 = xCoordinates[i];
            y1 = yCoordinates[i];
            x2 = xCoordinates[i + 1];
            y2 = yCoordinates[i + 1];
            x3 = xCoordinates[i + 2];
            y3 = yCoordinates[i + 2];

            if(0.5*(Math.abs(x1*y2+x2*y3+x3*y1-x1*y3-x2*y1-x3*y2))>areaThreshold) return true;
        }
        return false;
    }
}
