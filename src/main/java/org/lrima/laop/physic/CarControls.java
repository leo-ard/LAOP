package org.lrima.laop.physic;

/**
 * Used to transfer the array of data retrieved from an algorithm
 * to an object
 * @author Clement Bisaillon
 */
public class CarControls {
	public static final CarControls IDENTITY = new CarControls(0, 0);
	private double accelerationField;
	private double rotationField;

	public CarControls(){}

	/**
	 * Initiates the car controls from an array of values
	 * @param values the array of values
	 */
	public CarControls(double ... values){
		this.accelerationField = values[0];
		this.rotationField = values[1];
	}

	/**
	 * Initiate the car controls with specific fields
	 * @param accelerationField the acceleration of the car
	 * @param rotationField the retation of the steering wheel
	 */
	public CarControls(double accelerationField, double rotationField) {
		this.accelerationField = accelerationField;
		this.rotationField = rotationField;
	}

	/**
	 * @return The acceleration from the array received from an algorithm
	 */
	double getAcceleration() {
		if(Math.abs(accelerationField )< 0.0001){
			accelerationField = 0;
		}

		return this.accelerationField;
	}

	/**
	 * @return The rotation from the array received from an algorithm
	 */
	double getRotation() {
		return this.rotationField;
	}

	@Override
	public String toString() {
		return String.format("[%.2f, %.2f]", accelerationField, rotationField);
	}
}
