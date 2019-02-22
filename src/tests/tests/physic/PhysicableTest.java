package tests.physic;

import org.junit.Assert;
import org.junit.Test;
import org.lrima.laop.math.Vector3d;
import org.lrima.laop.physic.objects.Bloc;

/**
 * Test the Physicable class
 * @author Clement Bisaillon
 */
public class PhysicableTest {
    private double floatDelta = 0.0001;

    @Test
    public void sumOfForcesTest1(){
        Vector3d v1 = new Vector3d(1, 0, 0);
        Vector3d v2 = new Vector3d(-1, 0, 0);

        Bloc b1 = new Bloc(5, 100, 200);
        b1.addForce(v1);
        b1.addForce(v2);

        Vector3d sumForces1 = b1.getSumForces();
        Assert.assertEquals(0, sumForces1.getX(), this.floatDelta);
        Assert.assertEquals(0, sumForces1.getY(), this.floatDelta);
        Assert.assertEquals(0, sumForces1.getZ(), this.floatDelta);
    }

    @Test
    public void sumOfForcesTest2(){
        Vector3d v1 = new Vector3d(1, 0, 0);
        Vector3d v2 = new Vector3d(0, 2, 0);

        Bloc b1 = new Bloc(5, 100, 200);
        b1.addForce(v1);
        b1.addForce(v2);
        Vector3d sumForces1 = b1.getSumForces();
        Assert.assertEquals(1, sumForces1.getX(), this.floatDelta);
        Assert.assertEquals(2, sumForces1.getY(), this.floatDelta);
        Assert.assertEquals(0, sumForces1.getZ(), this.floatDelta);
    }

}
