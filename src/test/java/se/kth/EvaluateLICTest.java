package se.kth;

import java.awt.geom.Point2D;
import java.lang.annotation.Target;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

   // ---------------------------------------------------- LIC2 ----------------------------------------------------
   @Test
   public void testLIC2Positive(){
       EvaluateLIC eval = new EvaluateLIC();
       Point2D[] coordinates = new Point2D.Double[4];

       coordinates[0] = new Point2D.Double(0, 0.1);
       coordinates[1] = new Point2D.Double(0, 0);
       coordinates[2] = new Point2D.Double(0, 0.2);
       coordinates[3] = new Point2D.Double(4, 4);

       double epsilon = 0.1;

       assertTrue(eval.LIC2(coordinates,epsilon));
   }

   @Test
   public void testLIC2Negative(){
       EvaluateLIC eval = new EvaluateLIC();
       Point2D[] coordinates = new Point2D.Double[5];

       coordinates[0] = new Point2D.Double(0, 1);
       coordinates[1] = new Point2D.Double(0, 0);
       coordinates[2] = new Point2D.Double(1, 0);
       coordinates[3] = new Point2D.Double(2, 2);
       coordinates[4] = new Point2D.Double(3, 3);

       double epsilon = 2.0;

       assertFalse(eval.LIC2(coordinates,epsilon));
   }

   @Test
    public void testLIC2Epsilon(){
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];

        coordinates[0] = new Point2D.Double(0, 1);
        coordinates[1] = new Point2D.Double(0, 0);
        coordinates[2] = new Point2D.Double(1, 0);

        double epsilon1 = 1.0;
        double epsilon2 = 3.0;

        assertNotEquals(eval.LIC2(coordinates,epsilon1), eval.LIC2(coordinates,epsilon2));
    }

    @Test
    public void testExceptLIC2() {
        EvaluateLIC eval = new EvaluateLIC();
        // 2 tests for bad inputs, epsilon < 0, epsilon < Math.PI
        Point2D[] coordinates = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(2, 0)
        };
  
        double epsilon1 = -1;
        double epsilon2 = Math.PI + 1;


        // epsilon < 0
        try {
            eval.LIC2(coordinates,epsilon1);
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // epsilon > Math.PI 
        try {
            eval.LIC2(coordinates,epsilon2);
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }
    }


  // ---------------------------------------------------- LIC3 ----------------------------------------------------
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

   // ---------------------------------------------------- LIC6 ----------------------------------------------------
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
    public void testExceptLIC6() {
        EvaluateLIC eval = new EvaluateLIC();
        // 3 tests for bad inputs, 0 > dist, nPts < 3, nPts > numPoints
        Point2D[] coordinates = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(2, 0),
            new Point2D.Double(2, 0),
            new Point2D.Double(2, 0),
            new Point2D.Double(2, 0)
        };
        int nPts1 = 3;
        int nPts2 = 2;
        int nPts3 = 6;
        double dist1 = -2;
        double dist2 = 2;


        // 0 > dist
        try {
            eval.LIC6(coordinates, nPts1, dist1);
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // nPts < 3
        try {
            eval.LIC6(coordinates, nPts2, dist2);
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // nPts > numPoints
        try {
            eval.LIC6(coordinates, nPts3, dist2);
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }
    }
    
  // ---------------------------------------------------- LIC7 ----------------------------------------------------
    @Test
    public void testLIC7Positive(){
        // Case with a set of points kPts apart are further away than length1 exists
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates = new Point2D.Double[5];
        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 0);
        coordinates[2] = new Point2D.Double(2, 0);
        coordinates[3] = new Point2D.Double(1, 0);
        coordinates[4] = new Point2D.Double(5, 0);

        int kPts = 2;
        double length1 = 2;

        assertTrue(eval.LIC7(coordinates, kPts, length1));
    }

    @Test
    public void testLIC7PositiveMin(){
        // Minimal possible input
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 0), 
            new Point2D.Double(3, 0)
        };
        int kPts = 1;
        double length1 = 2.0;
        assertTrue(eval.LIC7(coordinates, kPts, length1));
    }

    @Test
    public void testLIC7Negative(){
        // Case where at a set of points kPts apart are further away than length1 does not exist
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[5];
        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 0);
        coordinates[2] = new Point2D.Double(2, 0);
        coordinates[3] = new Point2D.Double(1, 0);
        coordinates[4] = new Point2D.Double(2, 0);

        int kPts = 2;
        double length1 = 4;

        assertFalse(eval.LIC7(coordinates, kPts, length1));
    }

    @Test
    public void testExceptLIC7(){
        // 3 tests for bad input, kPts < 1, kPts > numPoints - 2, numPoints < 3
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates1 = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 0),
            new Point2D.Double(1, 0)
        };
        Point2D[] coordinates2 = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 0)
        };
        
        int kPts1 = 0;
        int kPts2 = 2;
        double length1 = 2;

        // kPts < 1
        try {
            eval.LIC7(coordinates1, kPts1, length1);
        } catch (AssertionError e) {
            // AssertionError is expected for kPts < 1
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // kPts > numPoints -2
        try {
            eval.LIC7(coordinates1, kPts2, length1);
        } catch (AssertionError e) {
            // AssertionError is expected for kPts < 1
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // numPOints < 3
        assertFalse(eval.LIC7(coordinates2, kPts2, length1));
    }

  // ---------------------------------------------------- LIC9 ----------------------------------------------------
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

    // Tests for LIC13
    @Test
    public void testLIC13SameCircle() {
        // Case whith guaranteed unpassable or guaranteed passable radiuses
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
        };
        assertFalse(eval.LIC13(coordinates, 0, 0, 1e10, 0.0));
        assertTrue(eval.LIC13(coordinates, 0, 0, 0, 1e10));
    }

    @Test
    public void testLIC13DifferentCircle() {
        // Case where different radiuses are used
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 10),
                new Point2D.Double(10, 10),
        };
        assertTrue(eval.LIC13(coordinates, 0, 0, 5.0, 1.0));
        assertFalse(eval.LIC13(coordinates, 0, 0, 5.0, 0.0));
        assertFalse(eval.LIC13(coordinates, 0, 0, 10.0, 1.0));
    }

    @Test
    public void testLIC13StackedPoints() {
        // Case where datapoints are overlapping
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0)
        };
        assertFalse(eval.LIC13(coordinates, 0, 0, 0.0, 0.0));
    }

    @Test
    public void testLIC13InvalidInput() {
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates1 = {
                new Point2D.Double(0, 0),
        };

        // invalid length for datapoints array
        assertFalse(eval.LIC13(coordinates1, 0, 0, 0.0, 0.0));

        Point2D[] coordinates2 = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
        };

        // invalid second radius
        try {
            eval.LIC13(coordinates2, 0, 0, 0.0, -1.0);
            fail("Should crash");
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }
    }
}
