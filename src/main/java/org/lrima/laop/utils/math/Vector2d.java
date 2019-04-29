package org.lrima.laop.utils.math;

import java.awt.geom.Point2D;

/**
 * Stores a two dimensions vector
 * @author Clement Bisaillon
 */
public class Vector2d implements Cloneable {
    public static Vector2d origin = new Vector2d(0, 0);
    private double x;
    private double y;

    private String tag;

    /**
     * Creates a new two dimensions vector from two controls
     * @param x the first value
     * @param y the second value
     */
    public Vector2d(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Clone the vector
     * @return the new vector
     */
    public Vector2d clone(){
        try {
            super.clone();
            return new Vector2d(this.x, this.y);
        }catch (CloneNotSupportedException e){
            System.err.println("Clone not supported !");
            return Vector2d.origin;
        }
    }

    /**
     * Scale the vector by a certain factor
     * @param k the scaling factor
     * @return the scaled vector
     */
    public Vector2d multiply(double k){
        return new Vector2d(this.x * k, this.y * k);
    }

    /**
     * Multiply two vectors
     * @param v the othe vector
     * @return the scaled vector
     */
    public Vector2d multiply(Vector2d v){
        return new Vector2d(this.x * v.x, this.y * v.y);
    }


    /**
     * Adds two vectors together
     * @param v2 the second vector to add
     * @return the resulting vector from the addition
     */
    public Vector2d add(Vector2d v2){
        return new Vector2d(this.x + v2.x, this.y + v2.y);
    }

    /**
     * Get the modulus of the vector
     * @return the modulus of the vector
     */
    public double modulus(){
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    /**
     * Normalize the vector
     * @return the normalized vector
     */
    public Vector2d normalize(){
        return this.multiply(1/this.modulus());
    }

    /**
     * Rotate the vector according to the angle in radians
     */
    public Vector2d rotate(double tetha, Vector2d axis){
        double x = ((this.x - axis.getX()) * Math.cos(tetha)) - ((this.y - axis.getY()) * Math.sin(tetha)) + axis.getX();
        double y = ((this.x - axis.getX()) * Math.sin(tetha)) + ((this.y - axis.getY()) * Math.cos(tetha)) + axis.getY();
        return new Vector2d(x, y);
    }

    /**
     * @return the x component of the vector
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y component of the vector
     */
    public double getY() {
        return y;
    }

    /**
     * @return the vector in a string
     */
    public String toString(){
        return String.format("[%.2f, %.2f]", this.x, this.y);
    }

    /**
     * @return the tag associated with the algorithm
     */
    public String getTag() {
        return tag;
    }


    /**
     * Power a vector by a factor 2
     * Faster (calcul related) then the other method power(2)
     *
     * @return a new vector with power 2
     */
    public Vector2d power2() {
        return new Vector2d(this.x * this.x, this.y * this.y);
    }

    /**
     * Created a vector that posses -1, 0 or 1 in its controls indicating witch sign is the vector at that coordonate
     *
     * @return a new vector with the proprieties listed above
     */
    public Vector2d sign() {
        return new Vector2d(Math.signum(this.x), Math.signum(this.y));
    }

    /**
     * Substrat two vectors.
     *
     * @param v the other vector
     * @return a new vector with that is the subtraction of the two others.
     */
    public Vector2d subtract(Vector2d v) {
        return new Vector2d(this.x - v.x, this.y - v.y);
    }

    /**
     * Project one vector unto the other
     *
     * @param v the other vector
     * @return a new vector that is the projection of one over the other
     */
    public Vector2d project(Vector2d v){
        double dot = v.x * this.x + v.y * this.y;
        double x = this.x * dot;
        double y = this.y * dot;
        return new Vector2d(x, y);
    }

    /**
     * Set the tag of the vector
     * @param tag the tag
     */
    public void setTag(String tag){
        this.tag = tag;
    }

    /**
     * @return the vector as a Point2D object
     */
    public Point2D asPoint() {
        return new Point2D.Double(this.getX(), this.getY());
    }
}
