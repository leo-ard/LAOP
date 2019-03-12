package java.laop_math;

import org.junit.Assert;
import org.junit.Test;
import org.lrima.laop.math.Vector2d;


/**
 * Test methods of Vector2d
 * @author Clement Bisaillon
 */
public class Vector2dTest {

    private double floatError = 0.00001;

    @Test
    public void instantiationTest(){
        Vector2d v = new Vector2d(1.444, -0.00002);
        Assert.assertEquals(1.444, v.getX(), this.floatError);
        Assert.assertEquals(-0.00002, v.getY(), this.floatError);
    }

    @Test
    public void cloningTest(){
        Vector2d v = new Vector2d(1, 2);
        Vector2d v2 = v.clone();

        Assert.assertEquals(v.getX(), v2.getX(), this.floatError);
        Assert.assertNotSame(v.getX(), v2.getX());

        Assert.assertEquals(v.getY(), v2.getY(), this.floatError);
        Assert.assertNotSame(v.getY(), v2.getY());
    }

    @Test
    public void multiplyTest(){
        Vector2d v = new Vector2d(1, 2);
        Vector2d v2 = v.multiply(1.5);

        Assert.assertEquals(1.5, v2.getX(), this.floatError);
        Assert.assertEquals(3.0, v2.getY(), this.floatError);
    }

    @Test
    public void modulusTest(){
        Vector2d v = new Vector2d(3, 4);

        Assert.assertEquals(5.0, v.modulus(), this.floatError);

        Vector2d v2 = new Vector2d(1, 1);
        Assert.assertEquals(Math.sqrt(2), v2.modulus(), this.floatError);
    }

    @Test
    public void normalizeTest(){
        Vector2d v = new Vector2d(2, -3);
        Vector2d normalizedV = v.normalize();

        Assert.assertEquals(2/Math.sqrt(13), normalizedV.getX(), this.floatError);
    }

    @Test
    public void addTest(){
        Vector2d v = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(-3, 7);
        Vector2d addV = v.add(v2);

        Assert.assertEquals(-2, addV.getX(), this.floatError);
        Assert.assertEquals(8, addV.getY(), this.floatError);

    }
}
