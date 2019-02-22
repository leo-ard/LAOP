package tests.math;

import org.junit.Assert;
import org.junit.Test;
import org.lrima.laop.math.Vector3d;


/**
 * Test methods of Vector3d
 * @author Clement Bisaillon
 */
public class Vector3dTest {

    private double floatError = 0.00001;

    @Test
    public void instantiationTest(){
        Vector3d v = new Vector3d(1.444, -0.00002, 0);
        Assert.assertEquals(1.444, v.getX(), this.floatError);
        Assert.assertEquals(-0.00002, v.getY(), this.floatError);
        Assert.assertEquals(0, v.getZ(), this.floatError);
    }

    @Test
    public void cloningTest(){
        Vector3d v = new Vector3d(1, 2, 0);
        Vector3d v2 = v.clone();

        Assert.assertEquals(v.getX(), v2.getX(), this.floatError);
        Assert.assertNotSame(v.getX(), v2.getX());

        Assert.assertEquals(v.getY(), v2.getY(), this.floatError);
        Assert.assertNotSame(v.getY(), v2.getY());

        Assert.assertEquals(v.getZ(), v2.getZ(), this.floatError);
        Assert.assertNotSame(v.getZ(), v2.getZ());
    }

    @Test
    public void multiplyTest(){
        Vector3d v = new Vector3d(1, 2, 0);
        Vector3d v2 = v.multiply(1.5);

        Assert.assertEquals(1.5, v2.getX(), this.floatError);
        Assert.assertEquals(3.0, v2.getY(), this.floatError);
        Assert.assertEquals(0, v2.getZ(), this.floatError);
    }

    @Test
    public void modulusTest(){
        Vector3d v = new Vector3d(3, 4, 0);

        Assert.assertEquals(5.0, v.modulus(), this.floatError);

        Vector3d v2 = new Vector3d(1, 1, 1);
        Assert.assertEquals(Math.sqrt(3), v2.modulus(), this.floatError);
    }

    @Test
    public void normalizeTest(){
        Vector3d v = new Vector3d(2, -3, 0);
        Vector3d normalizedV = v.normalize();

        Assert.assertEquals(2/Math.sqrt(13), normalizedV.getX(), this.floatError);
        Assert.assertEquals(-3/Math.sqrt(13), normalizedV.getY(), this.floatError);
        Assert.assertEquals(0/Math.sqrt(13), normalizedV.getZ(), this.floatError);
    }

    @Test
    public void addTest(){
        Vector3d v = new Vector3d(1, 1, 0);
        Vector3d v2 = new Vector3d(-3, 7, 1);
        Vector3d addV = v.add(v2);

        Assert.assertEquals(-2, addV.getX(), this.floatError);
        Assert.assertEquals(8, addV.getY(), this.floatError);
        Assert.assertEquals(1, addV.getZ(), this.floatError);

    }

    @Test
    public void addTest2(){
        Vector3d v = new Vector3d(1, 0, 0);
        Vector3d v2 = new Vector3d(0, 1, 1);
        Vector3d addV = v.add(v2);

        Assert.assertEquals(1, addV.getX(), this.floatError);
        Assert.assertEquals(1, addV.getY(), this.floatError);
        Assert.assertEquals(1, addV.getZ(), this.floatError);

    }
}
