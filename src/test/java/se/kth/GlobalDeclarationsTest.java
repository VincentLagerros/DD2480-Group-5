package se.kth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GlobalDeclarationsTest {

    @Test
    public void testGlobalInitialization() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();

        // Check arrays for coordinates
        assertEquals(100, globals.xCoordinates.length);
        assertEquals(100, globals.yCoordinates.length);

        // Check som of the boolean arrays
        for (boolean value : globals.cmv) {
            assertFalse(value);
        }
        for (boolean[] row : globals.pum) {
            for (boolean value : row) {
                assertFalse(value);
            }
        }

        // Check the Connectors matrix
        for (GlobalDeclarations.Connectors[] row : globals.lcm) {
            for (GlobalDeclarations.Connectors value : row) {
                assertEquals(GlobalDeclarations.Connectors.NOTUSED, value);
            }
        }
    }

    @Test
    public void testDoubleCompareFunction() {
        assertEquals(GlobalDeclarations.CompType.EQ, GlobalDeclarations.doubleCompare(1.0, 1.0));
        assertEquals(GlobalDeclarations.CompType.EQ, GlobalDeclarations.doubleCompare(1.0000001, 1.0000002));
        assertEquals(GlobalDeclarations.CompType.LT, GlobalDeclarations.doubleCompare(1.0, 2.0));
        assertEquals(GlobalDeclarations.CompType.GT, GlobalDeclarations.doubleCompare(2.0, 1.0));

        assertNotEquals(GlobalDeclarations.CompType.EQ, GlobalDeclarations.doubleCompare(1.0, 2.0));
        assertNotEquals(GlobalDeclarations.CompType.LT, GlobalDeclarations.doubleCompare(1.0, 1.0));
        assertNotEquals(GlobalDeclarations.CompType.GT, GlobalDeclarations.doubleCompare(1.0, 1.0));
    }   

    @Test
    public void testGlobalVariablesCorrecltyAssigned() {
        GlobalDeclarations.Globals globals = new GlobalDeclarations.Globals();

        globals.numPoints = 10;
        globals.parameters.LENGTH1 = 40.0;
        globals.cmv[0] = true;
        globals.lcm[0][0] = GlobalDeclarations.Connectors.ANDD;

        assertEquals(10, globals.numPoints);
        assertEquals(40.0, globals.parameters.LENGTH1, 0.00001);
        assertTrue(globals.cmv[0]);
        assertEquals(GlobalDeclarations.Connectors.ANDD, globals.lcm[0][0]);
    }
}
