package se.kth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MainTest {
    @Test
    public void testValidInputForDecide() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();

        // Set number of points
        globals.numPoints = 14;
        globals.xCoordinates = new double[]{0, 1, 2, 3, 4, 0, -1, 2, 3, 0, 0, 0, 12, 12};
        globals.yCoordinates = new double[]{0, 1, 0, 1, 0, 0, 2, 1, -2, 0, 0, 0, 12, 12};

        // Set LIC parameters to ensure conditions are met
        globals.parameters.LENGTH1 = 0.5;
        globals.parameters.RADIUS1 = 1.0;
        globals.parameters.EPSILON = 0.1;
        globals.parameters.AREA1 = 0.5;
        globals.parameters.Q_PTS = 3;
        globals.parameters.QUADS = 1;
        globals.parameters.N_PTS = 3;
        globals.parameters.A_PTS = 1;
        globals.parameters.B_PTS = 1;
        globals.parameters.DIST = 0.5;
        globals.parameters.K_PTS = 1;
        globals.parameters.C_PTS = 1;
        globals.parameters.D_PTS = 1;
        globals.parameters.E_PTS = 1;
        globals.parameters.F_PTS = 1;
        globals.parameters.G_PTS = 1;
        globals.parameters.LENGTH2 = 0.2;
        globals.parameters.RADIUS2 = 2.0;
        globals.parameters.AREA2 = 1.0;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                globals.lcm[i][j] = GlobalDeclarations.Connectors.ORR;
            }
        }

        // Call the DECIDE function
        Main.DECIDE(globals);

        assertTrue(globals.launchDecision);
    }

    @Test
    public void testInValidInputForDecide() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();

        // Set number of points
        globals.numPoints = 5;
        globals.xCoordinates = new double[]{0, 1, 2, 3, 4};
        globals.yCoordinates = new double[]{0, 1, 0, 1, 0};

        // Set LIC parameters to ensure conditions are not met
        globals.parameters.LENGTH1 = 0.5;
        globals.parameters.RADIUS1 = 1.0;
        globals.parameters.EPSILON = 0.1;
        globals.parameters.AREA1 = 0.5;
        globals.parameters.Q_PTS = 3;
        globals.parameters.QUADS = 1;
        globals.parameters.N_PTS = 3;
        globals.parameters.A_PTS = 1;
        globals.parameters.B_PTS = 1;
        globals.parameters.DIST = 0.5;
        globals.parameters.K_PTS = 1;
        globals.parameters.C_PTS = 1;
        globals.parameters.D_PTS = 1;
        globals.parameters.E_PTS = 1;
        globals.parameters.F_PTS = 1;
        globals.parameters.G_PTS = 1;
        globals.parameters.LENGTH2 = 0.2;
        globals.parameters.RADIUS2 = 2.0;
        globals.parameters.AREA2 = 1.0;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                globals.lcm[i][j] = GlobalDeclarations.Connectors.ORR;
            }
        }

        // Call the DECIDE function
        Main.DECIDE(globals);

        assertFalse(globals.launchDecision);
    }

    @Test
    public void testInInValidInputForDecide2() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();

        // Set number of points
        globals.numPoints = 14;
        globals.xCoordinates = new double[]{0, 1, 2, 3, 4, 0, -1, 2, 3, 0, 0, 0, 12, 12};
        globals.yCoordinates = new double[]{0, 1, 0, 1, 0, 0, 2, 1, -2, 0, 0, 0, 12, 12};

        // Set LIC parameters to ensure conditions are not met
        globals.parameters.LENGTH1 = 0.5;
        globals.parameters.RADIUS1 = 1.0;
        globals.parameters.EPSILON = 0.1;
        globals.parameters.AREA1 = 100;
        globals.parameters.Q_PTS = 3;
        globals.parameters.QUADS = 1;
        globals.parameters.N_PTS = 3;
        globals.parameters.A_PTS = 1;
        globals.parameters.B_PTS = 1;
        globals.parameters.DIST = 0.5;
        globals.parameters.K_PTS = 1;
        globals.parameters.C_PTS = 1;
        globals.parameters.D_PTS = 1;
        globals.parameters.E_PTS = 1;
        globals.parameters.F_PTS = 1;
        globals.parameters.G_PTS = 1;
        globals.parameters.LENGTH2 = 0.2;
        globals.parameters.RADIUS2 = 2.0;
        globals.parameters.AREA2 = 1.0;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                globals.lcm[i][j] = GlobalDeclarations.Connectors.ANDD;
            }
        }

        // Call the DECIDE function
        Main.DECIDE(globals);

        assertFalse(globals.launchDecision);
    }
}
