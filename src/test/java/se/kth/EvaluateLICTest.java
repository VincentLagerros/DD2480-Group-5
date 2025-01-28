package se.kth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EvaluateLICTest {
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
}
