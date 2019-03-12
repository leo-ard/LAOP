package graphics;

import org.junit.Assert;
import org.junit.Test;
import org.lrima.laop.graphics.AffineTransformation;
import org.lrima.laop.math.Vector3d;

/**
 * Test the class AffineTransformation
 * @author Clement Bisaillon
 */
public class AffineTransformationTest {

    private double deltaFloat = 0.0001;

    @Test
    public void testWorldToObject(){
        AffineTransformation af = new AffineTransformation(800, 400, 8);
        Vector3d pos = new Vector3d(4, 2, 0);

        Vector3d transformedPos = af.worldToObject(pos);

        Assert.assertEquals(400, transformedPos.getX(), this.deltaFloat);
        Assert.assertEquals(200, transformedPos.getY(), this.deltaFloat);
        Assert.assertEquals(0, transformedPos.getZ(), this.deltaFloat);
    }

    @Test
    public void testObjectToWorld(){
        AffineTransformation af = new AffineTransformation(800, 400, 8);
        Vector3d pos = new Vector3d(400, 200, 0);

        Vector3d transformedPos = af.objectToWorld(pos);

        Assert.assertEquals(4, transformedPos.getX(), this.deltaFloat);
        Assert.assertEquals(2, transformedPos.getY(), this.deltaFloat);
        Assert.assertEquals(0, transformedPos.getZ(), this.deltaFloat);
    }
}
