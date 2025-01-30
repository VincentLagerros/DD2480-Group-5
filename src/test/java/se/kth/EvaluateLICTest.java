package se.kth;

import java.awt.geom.Point2D;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

// Class for all unit tests relating to LIC evaluation
public class EvaluateLICTest {

    private Point2D[] convertToPoint2DArray(int numPoints, double[] xCoordinates, double[] yCoordinates) {
        assert xCoordinates.length == yCoordinates.length;
        Point2D[] coordinates = new Point2D.Double[numPoints];
        for (int i = 0; i < numPoints; i++) {
            coordinates[i] = new Point2D.Double(xCoordinates[i], yCoordinates[i]);
        }
        return coordinates;
    }

    // ---------------------------------------------------- LIC0 ----------------------------------------------------

    @Test
    public void testLIC0TrueForDistanceGreaterThanLength1() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 4, 7};
        globals.yCoordinates = new double[]{0, 0, 3, 0};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 3;

        assertTrue(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

    @Test
    public void testLIC0FalseForAllDistancesLessThanLength1() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 2, 0};
        globals.yCoordinates = new double[]{0, 1, 2, 0};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 5;

        assertFalse(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

    @Test
    public void testLIC0FalseForEdgeCaseEqualToLength1() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 3};
        globals.yCoordinates = new double[]{0, 0};
        globals.numPoints = 2;
        globals.parameters.LENGTH1 = 3;

        assertFalse(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

    @Test
    public void testLIC0FalseForInsufficientPoints() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0};
        globals.yCoordinates = new double[]{0};
        globals.numPoints = 1;
        globals.parameters.LENGTH1 = 0;

        assertFalse(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

    @Test
    public void testLIC0TrueForMultiplePairs() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 10, 15};
        globals.yCoordinates = new double[]{0, 1, 10, 15};
        globals.numPoints = 4;
        globals.parameters.LENGTH1 = 1;

        assertTrue(EvaluateLIC.LIC0(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.LENGTH1));
    }

  // ---------------------------------------------------- LIC1 ----------------------------------------------------

    @Test
    public void testLIC1TrueForTriangleOutsideRadius() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 4, 2};
        globals.yCoordinates = new double[]{0, 0, 3};
        globals.numPoints = 3;
        globals.parameters.RADIUS1 = 1;

        assertTrue(EvaluateLIC.LIC1(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.RADIUS1));
    }

    @Test
    public void testLIC1FalseForTriangleInsideRadius() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 2, 1};
        globals.yCoordinates = new double[]{0, 0, 1};
        globals.numPoints = 3;
        globals.parameters.RADIUS1 = 2;

        assertFalse(EvaluateLIC.LIC1(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.RADIUS1));
    }

    @Test
    public void testLIC1TrueForMultipleTrianglesWithOneExceedingRadius() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1, 2, 6};
        globals.yCoordinates = new double[]{0, 1, 0, 6};
        globals.numPoints = 4;
        globals.parameters.RADIUS1 = 2.5;

        assertTrue(EvaluateLIC.LIC1(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.RADIUS1));
    }

    @Test
    public void testLIC1FalseForPointsOnSingleLine() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 2, 4};
        globals.yCoordinates = new double[]{0, 0, 0};
        globals.numPoints = 3;
        globals.parameters.RADIUS1 = 6;

        assertFalse(EvaluateLIC.LIC1(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.RADIUS1));
    }

    @Test
    public void testLIC1FalseForInsufficientPoints() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 1};
        globals.yCoordinates = new double[]{0, 1};
        globals.numPoints = 2;
        globals.parameters.RADIUS1 = 1;

        assertFalse(EvaluateLIC.LIC1(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.RADIUS1));
    }

    @Test
    public void testLIC1TrueForPointsOnSingleLineButStillOutsideOfCircle(){
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        globals.xCoordinates = new double[]{0, 0, 0};
        globals.yCoordinates = new double[]{0, 100000, 200000};
        globals.numPoints = 3;
        globals.parameters.RADIUS1 = 1;

        assertTrue(EvaluateLIC.LIC1(convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates), globals.parameters.RADIUS1));
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

        assertTrue(eval.LIC2(coordinates, epsilon));
    }

    @Test
    public void testLIC2Negative() {
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[5];

        coordinates[0] = new Point2D.Double(0, 1);
        coordinates[1] = new Point2D.Double(0, 0);
        coordinates[2] = new Point2D.Double(1, 0);
        coordinates[3] = new Point2D.Double(2, 2);
        coordinates[4] = new Point2D.Double(3, 3);

        double epsilon = 2.0;

        assertFalse(eval.LIC2(coordinates, epsilon));
    }

    @Test
    public void testLIC2Epsilon() {
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];

        coordinates[0] = new Point2D.Double(0, 1);
        coordinates[1] = new Point2D.Double(0, 0);
        coordinates[2] = new Point2D.Double(1, 0);

        double epsilon1 = 1.0;
        double epsilon2 = 3.0;

        assertNotEquals(eval.LIC2(coordinates, epsilon1), eval.LIC2(coordinates, epsilon2));
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
            eval.LIC2(coordinates, epsilon1);
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // epsilon > Math.PI 
        try {
            eval.LIC2(coordinates, epsilon2);
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


    //Test for LIC4
    @Test
    public void testExeptLIC4() {
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];

        // clusterSize is to small
        try {
            eval.LIC4(coordinates, 1, 2);
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

        // quadsThreshold is too large
        try {
            eval.LIC4(coordinates, 2, 4);
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }

    }

    @Test
    public void testLIC4Positive() {
        //Case where at least one cluster of consecutive datapoints lie in more than quadrantThreshold quadrants
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(-1, 2);
        coordinates[2] = new Point2D.Double(2, 1);
        coordinates[3] = new Point2D.Double(3, -2);

        assertTrue(eval.LIC4(coordinates, 3, 2));
        assertTrue(eval.LIC4(coordinates, 2, 1));
        assertTrue(eval.LIC4(coordinates, 4, 2));
    }

    @Test
    public void testLIC4Negative() {
        //Case where no cluster of consecutive datapoints lie in more than quadrantThreshold quadrants
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 2);
        coordinates[2] = new Point2D.Double(-2, -1);
        coordinates[3] = new Point2D.Double(-3, -2);

        assertFalse(eval.LIC4(coordinates, 3, 2));
        assertFalse(eval.LIC4(coordinates, 4, 2));
        assertFalse(eval.LIC4(coordinates, 4, 3));
    }

    @Test
    public void testLIC4AxisCoordinates() {
        //Case where coordinates lie on an axis between two quadrants
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(0, 2);
        coordinates[2] = new Point2D.Double(-2, 0);
        coordinates[3] = new Point2D.Double(0, -2);

        assertTrue(eval.LIC4(coordinates, 2, 1));
        assertFalse(eval.LIC4(coordinates, 4, 3));
        assertTrue(eval.LIC4(coordinates, 3, 2));
    }

    // Tests for LIC5
    @Test
    public void testLIC5Positive() {
        // Case with points so that X[j] < X[i], i = j - 1 
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];
        coordinates[0] = new Point2D.Double(1, 0);
        coordinates[1] = new Point2D.Double(2, 0);
        coordinates[2] = new Point2D.Double(1.5, 0);

        assertTrue(m.LIC5(coordinates));
    }

    @Test
    public void testLIC5PositiveLast() {
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
    public void testLIC5Negative() {
        // Case with no points so that X[j] < X[i], i = j - 1 
        EvaluateLIC m = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];
        coordinates[0] = new Point2D.Double(1, 0);
        coordinates[1] = new Point2D.Double(2, 0);
        coordinates[2] = new Point2D.Double(3, 0);

        assertFalse(m.LIC5(coordinates));
    }

    @Test
    public void testLIC5NegativeShort() {
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
    public void testLIC7Positive() {
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
    public void testLIC7PositiveMin() {
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
    public void testLIC7Negative() {
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
    public void testExceptLIC7() {
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

  
  // ---------------------------------------------------- LIC8 ----------------------------------------------------
    @Test
    public void testLIC8TrueForTriangleOutsideRadius() {
        //Points that do not fit inside a circle of radius 0.1
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[5];
        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(2, 3);
        coordinates[2] = new Point2D.Double(5, 1);
        coordinates[3] = new Point2D.Double(7, 4);
        coordinates[4] = new Point2D.Double(10, 2);
        int aPts = 1;
        int bPts = 2;
        double radius = 0.1;
        assertTrue(eval.LIC8(coordinates, aPts, bPts, radius));
    }

    @Test
    public void testLIC8FalseForTriangleInsideRadius() {
        //Points that always fit within a circle of radius 10
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[5];
        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 1);
        coordinates[2] = new Point2D.Double(2, 0);
        coordinates[3] = new Point2D.Double(3, 1);
        coordinates[4] = new Point2D.Double(4, 0);
        int aPts = 1;
        int bPts = 2;
        double radius = 10;
        assertFalse(eval.LIC8(coordinates, aPts, bPts, radius));
    }

    @Test
    public void testLIC8FalseForInsufficientPoints() {
        //Only 4 points (condition requires 5)
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];
        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 1);
        coordinates[2] = new Point2D.Double(2, 0);
        coordinates[3] = new Point2D.Double(3, 1);
        int aPts = 1;
        int bPts = 2;
        double radius = 0.1;
        assertFalse(eval.LIC8(coordinates, aPts, bPts, radius));
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
        coordinates[2] = new Point2D.Double(1.0, 0.0);
        coordinates[4] = new Point2D.Double(1.0, 1.0);
        coordinates[1] = new Point2D.Double(1.0, 1.0);
        coordinates[3] = new Point2D.Double(1.0, 1.0);
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

        assertTrue(eval.LIC10(coordinates, ePts, fPts, area1));
        assertFalse(eval.LIC10(coordinates, ePts, fPts, area2));
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

        assertFalse(eval.LIC10(coordinates, ePts, fPts, area1));
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

    // ---------------------------------------------------- LIC13 ----------------------------------------------------
    
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

    // ---------------------------------------------------- LIC14 ----------------------------------------------------
    @Test
    public void testLIC14SameTriangle() {
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
        };
        assertFalse(eval.LIC14(coordinates, 0, 0, 1e10, 0.0));
        assertTrue(eval.LIC14(coordinates, 0, 0, 0.0, 1e10));
    }

    @Test
    public void testLIC14DifferentTriangle() {
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 10),
                new Point2D.Double(10, 10),
        };
        assertTrue(eval.LIC14(coordinates, 0, 0, 5.0, 1.0));
        assertFalse(eval.LIC14(coordinates, 0, 0, 5.0, 0.0));
    }

    @Test
    public void testLIC14InvalidInput() {
        EvaluateLIC eval = new EvaluateLIC();

        Point2D[] coordinates1 = {
                new Point2D.Double(0, 0),
        };

        assertFalse(eval.LIC14(coordinates1, 0, 0, 0.0, 0.0));

        Point2D[] coordinates2 = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 0),
        };
        try {
            eval.LIC14(coordinates2, 0, 0, 0.0, -1.0);
            fail("Should crash");
        } catch (AssertionError e) {
            assertTrue(e.getMessage() == null || e.getMessage().contains("assert"));
        }
    }
}
