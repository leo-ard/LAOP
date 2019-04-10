package laop_math;

import org.junit.jupiter.api.Test;
import org.lrima.laop.utils.math.Vector3d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;


/**
 * Test methods of Vector3d
 * @author Clement Bisaillon
 */
public class Vector3dTest {

    private double floatError = 0.00001;

    @Test
    public void instantiationTest(){
        Vector3d v = new Vector3d(1.444, -0.00002, 0);
        assertEquals(1.444, v.getX(), this.floatError);
        assertEquals(-0.00002, v.getY(), this.floatError);
        assertEquals(0, v.getZ(), this.floatError);
    }

    @Test
    public void cloningTest(){
        Vector3d v = new Vector3d(1, 2, 0);
        Vector3d v2 = v.clone();

        assertEquals(v.getX(), v2.getX(), this.floatError);
        assertNotSame(v.getX(), v2.getX());

        assertEquals(v.getY(), v2.getY(), this.floatError);
        assertNotSame(v.getY(), v2.getY());

        assertEquals(v.getZ(), v2.getZ(), this.floatError);
        assertNotSame(v.getZ(), v2.getZ());
    }

    @Test
    public void multiplyTest(){
        Vector3d v = new Vector3d(1, 2, 0);
        Vector3d v2 = v.multiply(1.5);

        assertEquals(1.5, v2.getX(), this.floatError);
        assertEquals(3.0, v2.getY(), this.floatError);
        assertEquals(0, v2.getZ(), this.floatError);
    }

    @Test
    public void modulusTest(){
        Vector3d v = new Vector3d(3, 4, 0);

        assertEquals(5.0, v.modulus(), this.floatError);

        Vector3d v2 = new Vector3d(1, 1, 1);
        assertEquals(Math.sqrt(3), v2.modulus(), this.floatError);
    }

    @Test
    public void normalizeTest(){
        Vector3d v = new Vector3d(2, -3, 0);
        Vector3d normalizedV = v.normalize();

        assertEquals(2/Math.sqrt(13), normalizedV.getX(), this.floatError);
        assertEquals(-3/Math.sqrt(13), normalizedV.getY(), this.floatError);
        assertEquals(0/Math.sqrt(13), normalizedV.getZ(), this.floatError);
    }

    @Test
    public void addTest(){
        Vector3d v = new Vector3d(1, 1, 0);
        Vector3d v2 = new Vector3d(-3, 7, 1);
        Vector3d addV = v.add(v2);

        assertEquals(-2, addV.getX(), this.floatError);
        assertEquals(8, addV.getY(), this.floatError);
        assertEquals(1, addV.getZ(), this.floatError);

    }

    @Test
    public void addTest2(){
        Vector3d v = new Vector3d(1, 0, 0);
        Vector3d v2 = new Vector3d(0, 1, 1);
        Vector3d addV = v.add(v2);

        assertEquals(1, addV.getX(), this.floatError);
        assertEquals(1, addV.getY(), this.floatError);
        assertEquals(1, addV.getZ(), this.floatError);

    }

    @Test
    public void distanceTest(){
        Vector3d a = new Vector3d(1, 0, 1);
        Vector3d b = new Vector3d(1, 1, 1);

        assertEquals(new Vector3d(0, 1, 0), Vector3d.distanceBetween(a,b));
    }

    @Test
    public void dotTest(){
        Vector3d a = new Vector3d(1, 6, -4);
        Vector3d b = new Vector3d(2, 6, 1);

        assertEquals(34.0, a.dot(b), this.floatError);
    }

    @Test
    public void angleTest(){
        Vector3d a = new Vector3d(4, 3, 2);
        Vector3d b = new Vector3d(6, -2, 3);

        assertEquals(0.880624451, Vector3d.angleBetween(a, b), this.floatError);
    }
}
