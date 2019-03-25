package org.lrima.laop.utils.math;

/**
 * Stores a three dimensions vector
 * @author Clement Bisaillon
 */
public class Vector3d implements Cloneable {
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
     * Calculates the distance between two locations
     * @param a the vector of the first position
     * @param b the vector of the second position
     * @return the distance between a and b
     */
    public static Vector3d distanceBetween(Vector3d a, Vector3d b){
        return b.add(a.multiply(-1));
    }

    /**
     * Calculated the angle between two vectors
     * @param a the first vector
     * @param b the second vector
     * @return the smallest angle between a and b
     */
    public static double angleBetween(Vector3d a, Vector3d b){
        double angle = Math.acos(a.dot(b) / (a.modulus() * b.modulus()));


        return angle;
    }

    /**
     * Get the the unit orientation vector
     * @return the orientation vector
     */
    public Vector3d orientation(){
        return this.multiply(1/this.modulus());
    }

    /**
     * Calculates the dot product between this vector and another
     * @param b the second vector
     * @return the dot product between two vectors
     */
    public double dot(Vector3d b){
        return this.x * b.getX() + this.y * b.getY() + this.z * b.getZ();
    }


    /**
     * Applies the cross product to the vector
     * @param b the second vector
     * @return the cros product between this vector and b
     */
    public Vector3d cross(Vector3d b){
        return new Vector3d(y*b.getZ() - b.getY()*z, -x*b.getZ() + b.getX()*z, x*b.getY() - y*b.getX());
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



    /**
     * Rotate the vector around an axis
     * @param tetha the angle of rotation
     * @return the rotated vector
     */
    public Vector3d rotateZAround(double tetha, Vector3d axis){
        double x = ((this.x - axis.getX()) * Math.cos(tetha)) - ((this.y - axis.getY()) * Math.sin(tetha)) + axis.getX();
        double y = ((this.x - axis.getX()) * Math.sin(tetha)) + ((this.y - axis.getY()) * Math.cos(tetha)) + axis.getY();

        return new Vector3d(x, y, 0);
    }

    /**
     * Project a vector on another
     * @param b the second vector
     * @return the vector projected on vector2
     */
    public Vector3d projection(Vector3d b){
        return b.normalize().multiply(this.dot(b)/Math.pow(b.modulus(),2));
    }


    @Override
    public boolean equals(Object obj) {
        Vector3d vec = (Vector3d) obj;
        return vec.getX() == this.getX() && vec.getY() == this.getY() && vec.getZ() == this.getZ();
    }

    @Override
    public String toString() {
        return "("+ this.x +" ; "+ this.y +" ; " + this.z +")";
    }
    
    /**
     * Display the vector with each component having only 2 decimals
     * @return the formated string of this vector
     */
    public String toFormatedString() {
    	String xFormated = String.format("%.2f", this.x);
    	String yFormated = String.format("%.2f", this.y);
    	String zFormated = String.format("%.2f", this.z);
    	
    	return "(" + xFormated + " ; " + yFormated + " ; " + zFormated + ")";
    }
}
