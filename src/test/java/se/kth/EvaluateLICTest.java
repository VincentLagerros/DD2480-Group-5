package se.kth;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.geom.Point2D;
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

    @Test
    public void testLIC6Positive() {
        // Case where at least one point lies farther than dist from the line
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 2);
        coordinates[2] = new Point2D.Double(2, 0);
        coordinates[3] = new Point2D.Double(3, 2);

        int numPoints = 4;
        int nPts = 3;
        double dist = 1.5;

        assertTrue(eval.LIC6(coordinates, numPoints, nPts, dist));
    }

    @Test
    public void testLIC6PositiveIdentical() {
        // Case where at least one point lies farther than dist from the first point and the first and last points in the group are identical
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 0);
        coordinates[2] = new Point2D.Double(0, 0);

        int numPoints = 3;
        int nPts = 3;
        double dist = 0.5;

        assertTrue(eval.LIC6(coordinates, numPoints, nPts, dist));
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

        int numPoints = 4;
        int nPts = 3;
        double dist = 1.5;

        assertFalse(eval.LIC6(coordinates, numPoints, nPts, dist));
    }

    @Test
    public void testLIC6NegativeShort() {
        // Case where numPoints < 3
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[4];

        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(1, 0);

        int numPoints = 2;
        int nPts = 3;
        double dist = 1.5;

        assertFalse(eval.LIC6(coordinates, numPoints, nPts, dist));
    }

    @Test
    public void testLIC6NegativeEdge() {
        // Case where numPoints = 3 and nPts=3
        EvaluateLIC eval = new EvaluateLIC();
        Point2D[] coordinates = new Point2D.Double[3];
        coordinates[0] = new Point2D.Double(0, 0);
        coordinates[1] = new Point2D.Double(0, 0);
        coordinates[2] = new Point2D.Double(0, 0);

        int numPoints = 3;
        int nPts = 3;
        double dist = 0.01;

        assertFalse(eval.LIC6(coordinates, numPoints, nPts, dist));
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

        int numPoints = 4;
        int nPts = 3;
        double dist = 0.01;

        assertFalse(eval.LIC6(coordinates, numPoints, nPts, dist));
    }
}
