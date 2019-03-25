package org.lrima.laop.math;

import java.util.Vector;

/**
 * Stores a two dimensions vector
 * @author Clement Bisaillon
 */
public class Vector2d implements Cloneable {
    public static Vector2d origin = new Vector2d(0, 0);
    private double x;
    private double y;

    /**
     * Creates a new two dimensions vector from two values
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
     * @param k the scaling factor
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
     * Rotate the vector according to the angle
     */
    public Vector2d rotate(double tetha, Vector2d axis){
        double x = ((this.x - axis.getX()) * Math.cos(tetha)) - ((this.y - axis.getY()) * Math.sin(tetha)) + axis.getX();
        double y = ((this.x - axis.getX()) * Math.sin(tetha)) + ((this.y - axis.getY()) * Math.cos(tetha)) + axis.getY();
        return new Vector2d(x, y);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public String toString(){
        return String.format("[%.2f, %.2f]", this.x, this.y);
    }

    public Vector2d power(int i) {
        return new Vector2d(Math.pow(this.x, i), Math.pow(this.y, i));
    }

    public Vector2d power2() {
        return new Vector2d(this.x * this.x, this.y * this.y);
    }

    public Vector2d sign() {
        return new Vector2d(Math.signum(this.x), Math.signum(this.y));
    }

    public double dot(Vector2d v) {
        return this.x * v.x + this.y * v.y;
    }

    public Vector2d subtract(Vector2d v) {
        return new Vector2d(this.x - v.x, this.y - v.y);
    }
}
