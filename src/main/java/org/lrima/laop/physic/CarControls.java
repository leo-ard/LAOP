package org.lrima.laop.physic;

/**
 * Used to transfer the array of data retrieved from an algorithm
 * to an object
 * @author Clement Bisaillon
 */
public class CarControls {
	private double accelerationField;
	private double breakField;
	private double rotationField;
	
	/**
	 * @return The acceleration from the array received from an algorithm
	 */
	public double getAcceleration() {
		return this.accelerationField;
	}
	
	/**
	 * @return The break from the array received from an algorithm
	 */
	public double getBreak() {
		return this.breakField;
	}
	
	/**
	 * @return The rotation from the array received from an algorithm
	 */
	public double getRotation() {
		return this.rotationField;
	}
	
	/**
	 * Sets the acceleration
	 * @param acceleration the acceleration
	 */
	public void setAcceleration(double acceleration) {
		this.accelerationField = acceleration;
	}
	
	/**
	 * Sets the break
	 * @param breakValue the break
	 */
	public void setBreak(double breakValue) {
		this.breakField = breakValue;
	}
	
	/**
	 * Sets the rotation
	 * @param rotation the rotation
	 */
	public void setRotation(double rotation) {
		this.rotationField = rotation;
	}
}
