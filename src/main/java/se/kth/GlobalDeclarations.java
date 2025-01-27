package se.kth;

import java.util.Arrays;

public class GlobalDeclarations {

    public enum Connectors {
        NOTUSED(777), ORR(1), ANDD(2);

        private final int value;

        Connectors(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum CompType {
        LT(1111), EQ(1112), GT(1113);

        private final int value;

        CompType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // inputs to the DECIDE() function
    public static class Parameters {
        public double LENGTH1; // Length in LICs 0, 7, 12
        public double RADIUS1; // Radius in LICs 1, 8, 13
        public double EPSILON; // Deviation from PI in LICs 2, 9
        public double AREA1;   // Area in LICs 3, 10, 14
        public int Q_PTS;      // No. of consecutive points in LIC 4
        public int QUADS;      // No. of quadrants in LIC 4
        public double DIST;    // Distance in LIC 6
        public int N_PTS;      // No. of consecutive pts. in LIC 6
        public int K_PTS;      // No. of int. pts. in LICs 7, 12
        public int A_PTS;      // No. of int. pts. in LICs 8, 13
        public int B_PTS;      // No. of int. pts. in LICs 8, 13
        public int C_PTS;      // No. of int. pts. in LIC 9
        public int D_PTS;      // No. of int. pts. in LIC 9
        public int E_PTS;      // No. of int. pts. in LICs 10, 14
        public int F_PTS;      // No. of int. pts. in LICs 10, 14
        public int G_PTS;      // No. of int. pts. in LIC 11
        public double LENGTH2; // Maximum length in LIC 12
        public double RADIUS2; // Maximum radius in LIC 13
        public double AREA2;   // Maximum area in LIC 14
    }

    // Global variables
    public static class Globals {
        public Parameters parameters = new Parameters();
        public double[] xCoordinates = new double[100]; // X coordinates of data points
        public double[] yCoordinates = new double[100]; // Y coordinates of data points
        public int numPoints; // Number of data points
        public Connectors[][] lcm = new Connectors[15][15]; // Logical Connector Matrix
        public boolean[][] pum = new boolean[15][15]; // Preliminary Unlocking Matrix
        public boolean[] cmv = new boolean[15]; // Conditions Met Vector
        public boolean[] fuv = new boolean[15]; // Final Unlocking Vector
        public boolean launchDecision; // Launch decision

        public Globals() {
            // Initialize Logical Connector Matrix with NOTUSED
            for (Connectors[] row : lcm) {
                Arrays.fill(row, Connectors.NOTUSED);
            }
        }
    }

    // Compares floating point numbers
    public static CompType doubleCompare(double a, double b) {
        if (Math.abs(a - b) < 0.000001) {
            return CompType.EQ;
        } else if (a < b) {
            return CompType.LT;
        } else {
            return CompType.GT;
        }
    }

}