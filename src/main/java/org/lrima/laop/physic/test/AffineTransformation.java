package org.lrima.laop.physic.test;

import org.lrima.laop.utils.math.Vector3d;

import java.awt.geom.AffineTransform;

/**
 * Class used to easily transfer from world coordinate system to object coordinate system.
 * @author Clement Bisaillon
 */
public class AffineTransformation {

    private AffineTransform worldToObject;
    private AffineTransform objectToWorld;

    private double worldWidth;
    private double objectWidth;
    private double objectHeight;

    /**
     * Prepare the matrices used to convert from world coordinate system to the object coordinate system
     * @param objectWidth the width of the view port in object dimensions (in pixels)
     * @param objectHeight the height of the view port in object dimensions (in pixels)
     * @param worldWidth the width of the view port in world dimensions (in meters)
     */
    public AffineTransformation(double objectWidth, double objectHeight, double worldWidth){
        this.worldWidth = worldWidth;
        this.objectWidth = objectWidth;
        this.objectHeight = objectHeight;

        double scale = worldWidth/objectWidth;

        worldToObject = new AffineTransform();
        worldToObject.scale(1 / scale,  1 / scale);

        objectToWorld = new AffineTransform();
        objectToWorld.scale(scale, scale);
    }

    /**
     * Transforms a three dimensional vector from world dimensions to object dimensions
     * @param v the vector to transform
     * @return the vector represented in object dimensions
     */
    public Vector3d worldToObject(Vector3d v){
        double[] matrix = new double[6];
        worldToObject.getMatrix(matrix);

        double x = v.getX() * matrix[0];
        double y = v.getY() * matrix[3];
        double z = 0;

        return new Vector3d(x, y, z);
    }

    /**
     * Transforms a three dimensional vector from object dimensions to world dimensions
     * @param v the vector to transform
     * @return the vector represented in world dimensions
     */
    public Vector3d objectToWorld(Vector3d v){
        double[] matrix = new double[6];
        worldToObject.getMatrix(matrix);

        double x = v.getX() / matrix[0];
        double y = v.getY() / matrix[3];
        double z = 0;

        return new Vector3d(x, y, z);
    }

    public AffineTransform getWorldToObject() {
        return worldToObject;
    }

    public AffineTransform getObjectToWorld() {
        return objectToWorld;
    }
}
