package se.kth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

// Class for all unit tests relating to LIC evaluation
public class EvaluateLICTest {
  // ---------------------------------------------------- LIC0 ----------------------------------------------------
    @Test
    public void testLIC0TrueForDistanceGreaterThanLength1(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 4, 7};
        globals.yCoordinates = new double[]{0, 0, 3, 0};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 3;

        assertTrue(EvaluateLIC.LIC0(globals.xCoordinates, globals.yCoordinates, globals.numPoints, globals.parameters.LENGTH1));
    }

    @Test
    public void testLIC0FalseForAllDistancesLessThanLength1(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 2, 0};
        globals.yCoordinates = new double[]{0, 1, 2, 0};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 5;

        assertFalse(EvaluateLIC.LIC0(globals.xCoordinates, globals.yCoordinates, globals.numPoints, globals.parameters.LENGTH1));
    }

    @Test
    public void testLIC0FalseForEdgeCaseEqualToLength1(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 3};
        globals.yCoordinates = new double[]{0, 0};
        globals.numPoints = 2;
        globals.parameters.LENGTH1 = 3;

        assertFalse(EvaluateLIC.LIC0(globals.xCoordinates, globals.yCoordinates, globals.numPoints, globals.parameters.LENGTH1));
    }

    @Test 
    public void testLIC0FalseForInsufficientPoints(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0};
        globals.yCoordinates = new double[]{0}; 
        globals.numPoints = 1;
        globals.parameters.LENGTH1 = 0;

        assertFalse(EvaluateLIC.LIC0(globals.xCoordinates, globals.yCoordinates, globals.numPoints, globals.parameters.LENGTH1));
    }

    @Test 
    public void testLIC0TrueForMultiplePairs(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 10, 15};
        globals.yCoordinates = new double[]{0, 1, 10, 15};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 1;

        assertTrue(EvaluateLIC.LIC0(globals.xCoordinates, globals.yCoordinates, globals.numPoints, globals.parameters.LENGTH1));
    }

  // ---------------------------------------------------- LIC6 ----------------------------------------------------
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
