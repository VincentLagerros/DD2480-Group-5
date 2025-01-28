package se.kth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EvaluateLICTest {
    
    // Tests for LIC3
    @Test
    public void testLIC3Positive(){
        //Case where a valid input is given
        EvaluateLIC m = new EvaluateLIC();
        double[] x = new double[10];
        double[] y = new double[10];
        x[9]=5;
        y[9]=5;
        x[8]=(-5);
        y[8]=5;
        double area = 0.5;

        assertTrue(m.LIC3(x,y,area));
    }

    @Test
    public void testLIC3NegativeForNoTriangle(){
        //Case where no points create a triangle with area>0
        EvaluateLIC m = new EvaluateLIC();
        double[] x = new double[10];
        double[] y = new double[10];
        x[9]=5;
        y[9]=5;
        double area = 0.5;

        assertFalse(m.LIC3(x,y,area));
    }

    @Test
    public void testLIC3NegativeForLargeAreaThreshold(){
        //Case where area threshold is larger than the triangle created by the points
        EvaluateLIC m = new EvaluateLIC();
        double[] x = new double[10];
        double[] y = new double[10];
        x[9]=5;
        y[9]=5;
        x[8]=(-5);
        y[8]=5;
        double area = 50;

        assertFalse(m.LIC3(x,y,area));
    }

    // Tests for LIC5
    @Test
    public void testLIC5Positive(){
        // Case with points so that X[j] < X[i], i = j - 1 
        EvaluateLIC m = new EvaluateLIC();
        double[] xCoordinates = {1.0, 2.0, 1.5};
        int numPoints = xCoordinates.length;

        assertTrue(m.LIC5(xCoordinates, numPoints));
    }

    @Test
    public void testLIC5PositiveLast(){
        // Case with points so that X[j] < X[i], i = j - 1 occurs at the final position in the array
        EvaluateLIC m = new EvaluateLIC();
        double[] xCoordinates = {1, 2, 3, 4, 5, 1};
        int numPoints = xCoordinates.length;

        assertTrue(m.LIC5(xCoordinates, numPoints));
    }

    @Test
    public void testLIC5Negative(){
        // Case with no points so that X[j] < X[i], i = j - 1 
        EvaluateLIC m = new EvaluateLIC();
        double[] xCoordinates = {1.0, 2.0, 3.0};
        int numPoints = xCoordinates.length;

        assertFalse(m.LIC5(xCoordinates, numPoints));
    }

    @Test
    public void testLIC5NegativeShort(){
        // Negative case with single point
        EvaluateLIC m = new EvaluateLIC();
        double[] xCoordinates = {1.0};
        int numPoints = xCoordinates.length;

        assertFalse(m.LIC5(xCoordinates, numPoints));
    }

    // Tests for LIC6
    @Test
    public void testLIC6Positive(){
        // Case where at least one point lies farther than dist from the line
        EvaluateLIC eval = new EvaluateLIC();
        double[] x = {0, 1, 2, 3};
        double[] y = {0, 2, 0, 2};
        int numPoints = 4;
        int nPts = 3;
        double dist = 1.5;

        assertTrue(eval.LIC6(x,y,numPoints, nPts, dist));
    }

    @Test
    public void testLIC6PositiveIdentical(){
        // Case where at least one point lies farther than dist from the first point and the first and last points in the group are identical
        EvaluateLIC eval = new EvaluateLIC();
        double[] x = {0, 1, 0};
        double[] y = {0, 0, 0};
        int numPoints = 3;
        int nPts = 3;
        double dist = 0.5;

        assertTrue(eval.LIC6(x,y,numPoints, nPts, dist));
    }

    @Test
    public void testLIC6Neagtive(){
        // Case where all points lie within dist from the line
        EvaluateLIC eval = new EvaluateLIC();
        double[] x = {0, 1, 2, 3};
        double[] y = {0, 0, 0, 0};
        int numPoints = 4;
        int nPts = 3;
        double dist = 1.5;

        assertFalse(eval.LIC6(x,y,numPoints, nPts, dist));
    }

    @Test
    public void testLIC6NeagtiveShort(){
        // Case where numPoints < 3
        EvaluateLIC eval = new EvaluateLIC();
        double[] x = {0, 1};
        double[] y = {0, 0};
        int numPoints = 2;
        int nPts = 3;
        double dist = 1.5;

        assertFalse(eval.LIC6(x,y,numPoints, nPts, dist));
    }

    @Test
    public void testLIC6NeagtiveEdge(){
        // Case where numPoints = 3 and nPts=3
        EvaluateLIC eval = new EvaluateLIC();
        double[] x = {0, 0, 0};
        double[] y = {0, 0, 0};
        int numPoints = 3;
        int nPts = 3;
        double dist = 0.01;

        assertFalse(eval.LIC6(x,y,numPoints, nPts, dist));
    }

    @Test
    public void testLIC6NeagtiveLine(){
        // Case where all points are on the same line
        EvaluateLIC eval = new EvaluateLIC();
        double[] x = {0, 1, 2, 3};
        double[] y = {0, 1, 2, 3};
        int numPoints = 4;
        int nPts = 3;
        double dist = 0.01;

        assertFalse(eval.LIC6(x,y,numPoints, nPts, dist));
    }
}
