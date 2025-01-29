package se.kth;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.Arrays;    

// Class for all unit tests relating to LIC evaluation
public class EvaluateLICTest {

    private Point2D[] convertToPoint2DArray(int numPoints, double[] xCoordinates, double[] yCoordinates) {
        assert xCoordinates.length == yCoordinates.length;
        Point2D[] coordinates = new Point2D.Double[numPoints];
        for (int i = 0; i < numPoints; i++){
            coordinates[i] = new Point2D.Double(xCoordinates[i], yCoordinates[i]);
        } 
        return coordinates;
    }

  // ---------------------------------------------------- LIC0 ----------------------------------------------------

    @Test
    public void testLIC0TrueForDistanceGreaterThanLength1(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 4, 7};
        globals.yCoordinates = new double[]{0, 0, 3, 0};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 3;

        assertTrue(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

    @Test
    public void testLIC0FalseForAllDistancesLessThanLength1(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 2, 0};
        globals.yCoordinates = new double[]{0, 1, 2, 0};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 5;

        assertFalse(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

    @Test
    public void testLIC0FalseForEdgeCaseEqualToLength1(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 3};
        globals.yCoordinates = new double[]{0, 0};
        globals.numPoints = 2;
        globals.parameters.LENGTH1 = 3;

        assertFalse(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

    @Test 
    public void testLIC0FalseForInsufficientPoints(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0};
        globals.yCoordinates = new double[]{0}; 
        globals.numPoints = 1;
        globals.parameters.LENGTH1 = 0;

        assertFalse(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

    @Test 
    public void testLIC0TrueForMultiplePairs(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 10, 15};
        globals.yCoordinates = new double[]{0, 1, 10, 15};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 1;

        assertTrue(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

  // ---------------------------------------------------- LIC6 ----------------------------------------------------
    @Test
    public void testLIC3Positive() {
        //Case where a valid input is given
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[10];
        Arrays.fill(coordinates, new Point2D.Double(0, 0));

        coordinates[9] = new Point2D.Double(5, 5);
        coordinates[8] = new Point2D.Double(-5, 5);
        double area = 0.5;

        assertTrue(m.LIC3(coordinates, area));
    }

    @Test
    public void testLIC3NegativeForNoTriangle() {
        //Case where no points create a triangle with area>0
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[10];
        Arrays.fill(coordinates, new Point2D.Double(0, 0));
        coordinates[9] = new Point2D.Double(5, 5);
        double area = 0.5;

        assertFalse(m.LIC3(coordinates, area));
    }

    @Test
    public void testLIC3NegativeForLargeAreaThreshold() {
        //Case where area threshold is larger than the triangle created by the points
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[10];
        Arrays.fill(coordinates, new Point2D.Double(0, 0));

        coordinates[9] = new Point2D.Double(5, 5);
        coordinates[8] = new Point2D.Double(-5, 5);
        double area = 50;

        assertFalse(m.LIC3(coordinates, area));
    }

    // Tests for LIC5
    @Test
    public void testLIC5Positive(){
        // Case with points so that X[j] < X[i], i = j - 1 
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];
        coordinates[0] = new Point2D.Double(1, 0);
        coordinates[1] = new Point2D.Double(2, 0);
        coordinates[2] = new Point2D.Double(1.5, 0);

        assertTrue(m.LIC5(coordinates));
    }

    @Test
    public void testLIC5PositiveLast(){
        // Case with points so that X[j] < X[i], i = j - 1 occurs at the final position in the array
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[6];
        coordinates[0] = new Point2D.Double(1, 0);
        coordinates[1] = new Point2D.Double(2, 0);
        coordinates[2] = new Point2D.Double(3, 0);
        coordinates[3] = new Point2D.Double(4, 0);
        coordinates[4] = new Point2D.Double(5, 0);
        coordinates[5] = new Point2D.Double(1, 0);

        assertTrue(m.LIC5(coordinates));
    }

    @Test
    public void testLIC5Negative(){
        // Case with no points so that X[j] < X[i], i = j - 1 
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];
        coordinates[0] = new Point2D.Double(1, 0);
        coordinates[1] = new Point2D.Double(2, 0);
        coordinates[2] = new Point2D.Double(3, 0);

        assertFalse(m.LIC5(coordinates));
    }

    @Test
    public void testLIC5NegativeShort(){
        // Negative case with single point
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[1];
        coordinates[0] = new Point2D.Double(1, 0);

        assertFalse(m.LIC5(coordinates));
    }

    // Tests for LIC6
    @Test
    public void testLIC6Positive() {
        // Case where at least one point lies farther than dist from the line
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 2);
        coordinates[2] = new Point2D.Double(2, 0);
        coordinates[3] = new Point2D.Double(3, 2);

        int nPts = 3;
        double dist = 1.5;

        assertTrue(eval.LIC6(coordinates, nPts, dist));
    }

    @Test
    public void testLIC6PositiveIdentical() {
        // Case where at least one point lies farther than dist from the first point and the first and last points in the group are identical
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 0);
        coordinates[2] = new Point2D.Double(0, 0);

        int nPts = 3;
        double dist = 0.5;

        assertTrue(eval.LIC6(coordinates, nPts, dist));
    }

    @Test
    public void testLIC6Neagtive() {
        // Case where all points lie within dist from the line
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 0);
        coordinates[2] = new Point2D.Double(2, 0);
        coordinates[3] = new Point2D.Double(3, 0);

        int nPts = 3;
        double dist = 1.5;

        assertFalse(eval.LIC6(coordinates, nPts, dist));
    }

    @Test
    public void testLIC6NegativeShort() {
        // Case where numPoints < 3
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];
        Arrays.fill(coordinates, new Point2D.Double(0, 0));

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 0);

        int nPts = 3;
        double dist = 1.5;

        assertFalse(eval.LIC6(coordinates, nPts, dist));
    }

    @Test
    public void testLIC6NegativeEdge() {
        // Case where numPoints = 3 and nPts=3
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];
        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(0, 0);
        coordinates[2] = new Point2D.Double(0, 0);

        int nPts = 3;
        double dist = 0.01;

        assertFalse(eval.LIC6(coordinates, nPts, dist));
    }

    @Test
    public void testLIC6NegativeLine() {
        // Case where all points are on the same line
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];
        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 1);
        coordinates[2] = new Point2D.Double(2, 2);
        coordinates[3] = new Point2D.Double(3, 3);

        int nPts = 3;
        double dist = 0.01;

        assertFalse(eval.LIC6(coordinates, nPts, dist));
    }


    @Test
    public void testLIC9Coincide() {
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[100];
        Arrays.fill(coordinates, new Point2D.Double(0, 0));
        assertFalse(eval.LIC9(coordinates, 1, 1, 0.001));
        // epsilon = PI means that if any 3 valid points exists, then it should return, however not if they intersect
        assertFalse(eval.LIC9(coordinates, 1, 1, -Math.PI));
        assertFalse(eval.LIC9(coordinates, 10, 10, -1000));
    }

    @Test
    public void testLIC9Any() {
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[100];
        Arrays.fill(coordinates, new Point2D.Double(0, 0));
        coordinates[10] = new Point2D.Double(1.0, 1.0);
        // will always find something, no matter the input (besides Coincide)
        assertTrue(eval.LIC9(coordinates, 1, 1, -10000.0));
        // the point is 0deg so any eps < pi works
        assertTrue(eval.LIC9(coordinates, 1, 1, 0.0));
        // always false, as eps is too large
        assertFalse(eval.LIC9(coordinates, 1, 1, Math.PI + 1.0));
    }

    @Test
    public void testLIC9_90deg() {
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[5];
        coordinates[0] = new Point2D.Double(0.0, 0.0);
        coordinates[1] = new Point2D.Double(1.0, 0.0);
        coordinates[2] = new Point2D.Double(1.0, 1.0);
        // numPoints >= 5
        coordinates[3] = new Point2D.Double(1.0, 1.0);
        coordinates[4] = new Point2D.Double(1.0, 1.0);
        assertTrue(eval.LIC9(coordinates, 1, 1, 0.0));
        assertFalse(eval.LIC9(coordinates, 1, 1, Math.PI / 2.0 + 0.1));
        assertTrue(eval.LIC9(coordinates, 1, 1, Math.PI / 2.0 - 0.1));
    }

    // ---------------------------------------------------- LIC10 ----------------------------------------------------
    @Test
    public void testLIC10positive() {
        EvaluateLIC eval = new EvaluateLIC();
        // Positive input points 
        Point2D[] coordinates = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(5, 5), 
            new Point2D.Double(10, 10), 
            new Point2D.Double(15, 5), 
            new Point2D.Double(20, 0),
            new Point2D.Double(20, 0),
            new Point2D.Double(-5, 0)
        };
        int ePts1 = 1;
        int ePts2 = 2;
        int fPts = 1;
        double area1 = 99.9;
        double area2 = 62;

        assertTrue(eval.LIC10(coordinates, ePts1, fPts, area1));
        assertTrue(eval.LIC10(coordinates, ePts2, fPts, area2));
    }
   
    @Test
    public void testLIC10Min() {
        EvaluateLIC eval = new EvaluateLIC();
        // positive and negative minimum possible input, numPoints = 5
        Point2D[] coordinates = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 1), 
            new Point2D.Double(2, 2), 
            new Point2D.Double(3, 3), 
            new Point2D.Double(4, 0)
        };
        int ePts = 1;
        int fPts = 1;
        double area1 = 3;
        double area2 = 4;

        assertTrue(eval.LIC10(coordinates, ePts, fPts,area1));
        assertFalse(eval.LIC10(coordinates, ePts, fPts,area2));
    }

    @Test
    public void testLIC10Neagtive() {
        EvaluateLIC eval = new EvaluateLIC();
        // negative input max area = 13.5, with edge area = area1
        Point2D[] coordinates = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 1), 
            new Point2D.Double(2, 0), 
            new Point2D.Double(3, 3), 
            new Point2D.Double(1, 10),
            new Point2D.Double(1, 10)
        };
        int ePts = 2;
        int fPts = 1;
        double area1 = 14;
        double area2 = 13.5;
        
        assertFalse(eval.LIC10(coordinates, ePts, fPts, area1));
        assertFalse(eval.LIC10(coordinates, ePts, fPts, area2));

    }

    @Test
    public void testLIC10NeagtiveCo() {
        EvaluateLIC eval = new EvaluateLIC();
        // negative coolinear input
        Point2D[] coordinates = {
            new Point2D.Double(-1, -1), 
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 1), 
            new Point2D.Double(2, 2), 
            new Point2D.Double(3, 3), 
            new Point2D.Double(4, 4)
        };
        int ePts = 1;
        int fPts = 1;
        double area1 = 0.1;
        
        assertFalse(eval.LIC10(coordinates, ePts, fPts,area1));
    }

    @Test
    public void testExceptLIC10() {
        EvaluateLIC eval = new EvaluateLIC();
        // 4 tests for bad inputs, ePts < 1, fPts < 1, ePts + fPts > numPoints - 3, NUMPOINTS < 5
        Point2D[] coordinates = {
            new Point2D.Double(-1, -1), 
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 1), 
            new Point2D.Double(2, 2), 
            new Point2D.Double(3, 3), 
            new Point2D.Double(4, 4)
        };

        Point2D[] coordinatesShort = {
            new Point2D.Double(-1, -1), 
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 1)
        };
        
        int ePts1 = 0;
        int ePts2 = 1;
        int ePts3 = 2;
        int fPts1 = -1;
        int fPts2 = 1;
        int fPts3 = 2;

        double area1 = 0.1;

        // ePts < 1
        try {
        assertFalse(eval.LIC10(coordinates, ePts1, fPts2, area1));
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // fPts < 1
        try {
            assertFalse(eval.LIC10(coordinates, ePts2, fPts1, area1));
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // ePts + fPts > numPoints - 3
        try {
            assertFalse(eval.LIC10(coordinates, ePts3, fPts3, area1));
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // NUMPOINTS < 5
        assertFalse(eval.LIC10(coordinatesShort, ePts2, fPts2, area1));
    }

    // ---------------------------------------------------- LIC11 ----------------------------------------------------
    @Test
    public void testLIC11positive() {
        EvaluateLIC eval = new EvaluateLIC();
        // Positive input points, checks multiple cases including when j is last, and when i is first in the array
        Point2D[] coordinates = {
            new Point2D.Double(11, 0), 
            new Point2D.Double(10, 5), 
            new Point2D.Double(-1, 10),
            new Point2D.Double(8, 10),
            new Point2D.Double(10, 10)
        };
        int gPts1 = 1;
        int gPts2 = 2;
        int gPts3 = 3; // Maximum valid gPts for these points

        assertTrue(eval.LIC11(coordinates, gPts1));
        assertTrue(eval.LIC11(coordinates, gPts2));
        assertTrue(eval.LIC11(coordinates, gPts3));
    }

    @Test
    public void testLIC11NegativeLarge() {
        EvaluateLIC eval = new EvaluateLIC();
        // Large array with negative input points
        Point2D[] coordinates = new Point2D[1000];

        // Increasing x values -> function should return false
        for (int i = 0; i < 1000; i++) {
            coordinates[i] = new Point2D.Double(i, 0);
        }
        int gPts = 10;

        assertFalse(eval.LIC11(coordinates, gPts));
    }

    @Test
    public void testLIC11negative() {
        EvaluateLIC eval = new EvaluateLIC();
        // Negative input points, checks multiple cases including when j is last, and when i is first in the array
        Point2D[] coordinates = {
            new Point2D.Double(-1, 0), 
            new Point2D.Double(0, 5), 
            new Point2D.Double(2, 10),
            new Point2D.Double(3, -10),
            new Point2D.Double(1000, 10)
        };
        int gPts1 = 1;
        int gPts2 = 2;
        int gPts3 = 3; // Maximum valid gPts for these points

        assertFalse(eval.LIC11(coordinates, gPts1));
        assertFalse(eval.LIC11(coordinates, gPts2));
        assertFalse(eval.LIC11(coordinates, gPts3));
    }

    @Test
    public void testLIC11negativeMin() {
        EvaluateLIC eval = new EvaluateLIC();
        // Negative input numpoints = 2
        Point2D[] coordinates = {
            new Point2D.Double(-1, 0), 
            new Point2D.Double(0, 5), 
            new Point2D.Double(2, 10)
        };
        int gPts1 = 1;

        assertFalse(eval.LIC11(coordinates, gPts1));
    }

    @Test
    public void testExceptLIC11() {
        EvaluateLIC eval = new EvaluateLIC();
        // 3 tests for bad inputs, gPts < 1, gPts > numPoints - 2, NUMPOINTS < 3
        Point2D[] coordinates = {
            new Point2D.Double(-1, -1), 
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 1), 
            new Point2D.Double(2, 2) 
        };
        Point2D[] coordinatesShort = {
            new Point2D.Double(-1, -1), 
            new Point2D.Double(0, 0) 
        };
        
        int gPts1 = -1;
        int gPts2 = 5;
    
        // gPts < 1
        try {
        assertFalse(eval.LIC11(coordinates, gPts1));
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // gPts > numPoints - 2
        try {
            assertFalse(eval.LIC11(coordinates, gPts2));
            } catch (AssertionError e) {
                assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
            }

        // NUMPOINTS < 5
        assertFalse(eval.LIC11(coordinatesShort, gPts2));
    }

}
