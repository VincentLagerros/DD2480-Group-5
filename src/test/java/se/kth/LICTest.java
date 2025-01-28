package se.kth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

// Class for all unit tests relating to LIC evaluation
public class LICTest {
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
}
