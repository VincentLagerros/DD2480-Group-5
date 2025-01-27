package se.kth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LIC5Test {
    @Test
    public void testLIC5Positive(){
        Main m = new Main();
        double[] xCoordinates = {1.0, 2.0, 1.5};

        assertTrue(m.LIC5(xCoordinates));
    }

    @Test
    public void testLIC5Negative(){
        Main m = new Main();
        double[] xCoordinates = {1.0, 2.0, 3.0};
        assertFalse(m.LIC5(xCoordinates));
    }

    @Test
    public void testLIC5NegativeShort(){
        Main m = new Main();
        double[] xCoordinates = {1.0};

        assertFalse(m.LIC5(xCoordinates));
    }
}
