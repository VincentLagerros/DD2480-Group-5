package se.kth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LICTest {
    @Test
    public void testLIC3Positive(){
        EvaluateLIC m = new EvaluateLIC();
        double[] x = new double[10];
        double[] y = new double[10];
        x[9]=5;
        y[9]=5;
        x[8]=(-5);
        y[8]=5;
        double area = 0.5;

        assertTrue(m.LIC3(x,y,area));
    }

    @Test
    public void testLIC3NegativeForNoTriangle(){
        EvaluateLIC m = new EvaluateLIC();
        double[] x = new double[10];
        double[] y = new double[10];
        x[9]=5;
        y[9]=5;
        double area = 0.5;

        assertFalse(m.LIC3(x,y,area));
    }

    @Test
    public void testLIC3NegativeForLargeAreaThreshold(){
        EvaluateLIC m = new EvaluateLIC();
        double[] x = new double[10];
        double[] y = new double[10];
        x[9]=5;
        y[9]=5;
        x[8]=(-5);
        y[8]=5;
        double area = 50;

        assertFalse(m.LIC3(x,y,area));
    }
}
