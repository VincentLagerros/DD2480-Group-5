package se.kth;

import java.awt.geom.Point2D;

public class Main {
    public static void DECIDE(GlobalDeclarations.Globals globals){
        Point2D[] coordinates = convertToPoint2DArray(globals.numPoints, globals.xCoordinates, globals.yCoordinates);

        // Conditions Met Vector (CMV)
        EvaluateLIC eval = new EvaluateLIC();
        globals.cmv[0] = EvaluateLIC.LIC0(coordinates, globals.parameters.LENGTH1);
        globals.cmv[1] = EvaluateLIC.LIC1(coordinates, globals.parameters.RADIUS1);
        globals.cmv[2] = eval.LIC2(coordinates, globals.parameters.EPSILON);
        globals.cmv[3] = eval.LIC3(coordinates, globals.parameters.AREA1);
        globals.cmv[4] = eval.LIC4(coordinates, globals.parameters.Q_PTS, globals.parameters.QUADS);
        globals.cmv[5] = eval.LIC5(coordinates);
        globals.cmv[6] = eval.LIC6(coordinates, globals.parameters.N_PTS, globals.parameters.DIST);
        globals.cmv[7] = eval.LIC7(coordinates, globals.parameters.K_PTS, globals.parameters.LENGTH1);
        globals.cmv[8] = eval.LIC8(coordinates, globals.parameters.A_PTS, globals.parameters.B_PTS, globals.parameters.RADIUS1);
        globals.cmv[9] = eval.LIC9(coordinates, globals.parameters.C_PTS, globals.parameters.D_PTS, globals.parameters.EPSILON);
        globals.cmv[10] = eval.LIC10(coordinates, globals.parameters.E_PTS, globals.parameters.F_PTS, globals.parameters.AREA1);
        globals.cmv[11] = eval.LIC11(coordinates, globals.parameters.G_PTS);
        globals.cmv[12] = eval.LIC12(coordinates, globals.parameters.K_PTS, globals.parameters.LENGTH2);
        globals.cmv[13] = eval.LIC13(coordinates, globals.parameters.A_PTS,globals.parameters.B_PTS,globals.parameters.RADIUS1,globals.parameters.RADIUS2);
        globals.cmv[14] = eval.LIC14(coordinates, globals.parameters.E_PTS,globals.parameters.F_PTS,globals.parameters.AREA1,globals.parameters.AREA2);
        
        // Preliminary Unlocking Matrix (PUM)
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (globals.lcm[i][j] == GlobalDeclarations.Connectors.NOTUSED) {
                    globals.pum[i][j] = true;
                } else if (globals.lcm[i][j] == GlobalDeclarations.Connectors.ANDD) {
                    globals.pum[i][j] = globals.cmv[i] && globals.cmv[j];
                } else if (globals.lcm[i][j] == GlobalDeclarations.Connectors.ORR) {
                    globals.pum[i][j] = globals.cmv[i] || globals.cmv[j];
                }
            }
        }

        // Final Unlocking Vector (FUV)
        for (int i = 0; i < 15; i++) {
            if (!globals.fuv[i]) {
                globals.fuv[i] = true;
                for (int j = 0; j < 15; j++) {
                    if (!globals.pum[i][j]) {
                        globals.fuv[i] = false;
                        break;
                    }
                }
            }
        }

        // Compute Final Launch Decision
        for (int i = 0; i < globals.fuv.length; i++) {
            if (globals.fuv[i]) {
                globals.launchDecision = true;
            }
        }
         
        // Print Launch Decision
        System.out.println(globals.launchDecision ? "YES" : "NO");
    }

    private static Point2D[] convertToPoint2DArray(int numPoints, double[] xCoordinates, double[] yCoordinates) {
        assert xCoordinates.length == yCoordinates.length;
        Point2D[] coordinates = new Point2D.Double[numPoints];
        for (int i = 0; i < numPoints; i++){
            coordinates[i] = new Point2D.Double(xCoordinates[i], yCoordinates[i]);
        } 
        return coordinates;
    }
    public static void main(String[] args) {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();
        
        //init all globals
        globals.numPoints = 5;
        globals.xCoordinates = new double[]{0, 0, 0, 0, 0};
        globals.yCoordinates = new double[]{0, 0, 0, 0, 0};
        globals.parameters.LENGTH1 = 0;
        globals.parameters.RADIUS1 = 0;
        globals.parameters.EPSILON = 0;
        globals.parameters.AREA1 = 0;
        globals.parameters.Q_PTS = 0;
        globals.parameters.QUADS = 0;
        globals.parameters.N_PTS = 0;
        globals.parameters.DIST = 0;
        globals.parameters.K_PTS = 0;
        globals.parameters.C_PTS = 0;
        globals.parameters.D_PTS = 0;
        globals.parameters.E_PTS = 0;
        globals.parameters.F_PTS = 0;
        globals.parameters.G_PTS = 0;

        DECIDE(globals);
    }

   
}
