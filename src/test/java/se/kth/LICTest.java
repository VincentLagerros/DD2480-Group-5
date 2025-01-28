package se.kth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LICTest {
    @Test
    public void testLIC5Positive(){
        EvaluateLIC m = new EvaluateLIC();
        double[] xCoordinates = {1.0, 2.0, 1.5};

        assertTrue(m.LIC5(xCoordinates));
    }

    @Test
    public void testLIC5Negative(){
        EvaluateLIC m = new EvaluateLIC();
        double[] xCoordinates = {1.0, 2.0, 3.0};
        assertFalse(m.LIC5(xCoordinates));
    }

    @Test
    public void testLIC5NegativeShort(){
        EvaluateLIC m = new EvaluateLIC();
        double[] xCoordinates = {1.0};

        assertFalse(m.LIC5(xCoordinates));
    }
}
