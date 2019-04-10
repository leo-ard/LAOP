package laop_math;

import org.junit.jupiter.api.Test;
import org.lrima.laop.utils.math.Vector2d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;


/**
 * Test methods of Vector2d
 * @author Clement Bisaillon
 */
public class Vector2dTest {

    private double floatError = 0.00001;

    @Test
    public void instantiationTest(){
        Vector2d v = new Vector2d(1.444, -0.00002);
        assertEquals(1.444, v.getX(), this.floatError);
        assertEquals(-0.00002, v.getY(), this.floatError);
    }

    @Test
    public void cloningTest(){
        Vector2d v = new Vector2d(1, 2);
        Vector2d v2 = v.clone();

        assertEquals(v.getX(), v2.getX(), this.floatError);
        assertNotSame(v.getX(), v2.getX());

        assertEquals(v.getY(), v2.getY(), this.floatError);
        assertNotSame(v.getY(), v2.getY());
    }

    @Test
    public void multiplyTest(){
        Vector2d v = new Vector2d(1, 2);
        Vector2d v2 = v.multiply(1.5);

        assertEquals(1.5, v2.getX(), this.floatError);
        assertEquals(3.0, v2.getY(), this.floatError);
    }

    @Test
    public void modulusTest(){
        Vector2d v = new Vector2d(3, 4);

        assertEquals(5.0, v.modulus(), this.floatError);

        Vector2d v2 = new Vector2d(1, 1);
        assertEquals(Math.sqrt(2), v2.modulus(), this.floatError);
    }

    @Test
    public void normalizeTest(){
        Vector2d v = new Vector2d(2, -3);
        Vector2d normalizedV = v.normalize();

        assertEquals(2/Math.sqrt(13), normalizedV.getX(), this.floatError);
    }

    @Test
    public void addTest(){
        Vector2d v = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(-3, 7);
        Vector2d addV = v.add(v2);

        assertEquals(-2, addV.getX(), this.floatError);
        assertEquals(8, addV.getY(), this.floatError);

    }
}
