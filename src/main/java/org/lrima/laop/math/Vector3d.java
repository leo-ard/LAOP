package org.lrima.laop.math;

/**
 * Stores a three dimensions vector
 * @author Clement Bisaillon
 */
public class Vector3d {
    private double x;
    private double y;
    private double z;

    public static Vector3d origin = new Vector3d(0, 0, 0);


    /**
     * Creates a new three dimensions vector from two values
     * @param x the first value
     * @param y the second value
     * @param z the third value
     */
    public Vector3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a vector at the origin
     */
    public Vector3d(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Clone the vector
     * @return the new vector
     */
    public Vector3d clone(){
        try {
            super.clone();
            return new Vector3d(this.x, this.y, this.z);
        }catch (CloneNotSupportedException e){
            System.err.println("Clone not supported !");
            return Vector3d.origin;
        }
    }

    /**
     * Scale the vector by a certain factor
     * @param k the scaling factor
     * @return the scaled vector
     */
    public Vector3d multiply(double k){
        return new Vector3d(this.x * k, this.y * k, this.z * k);
    }

    /**
     * Adds two vectors together
     * @param v2 the second vector to add
     * @return the resulting vector from the addition
     */
    public Vector3d add(Vector3d v2){
        return new Vector3d(this.x + v2.x, this.y + v2.y, this.z + v2.z);
    }

    /**
     * Get the modulus of the vector
     * @return the modulus of the vector
     */
    public double modulus(){
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

    /**
     * Normalize the vector
     * @return the normalized vector
     */
    public Vector3d normalize(){
        return this.multiply(1/this.modulus());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "("+ this.x +" ; "+ this.y +" ; " + this.z +")";
    }
}
