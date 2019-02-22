package math;

/**
 * Stores a two dimensions vector
 * @author Clement Bisaillon
 */
public class Vector2d {
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
        return new Vector2d(this.x, this.y);
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
}
