package org.lrima.laop.simulation.sensors;
/**
 * Interface representing a sensor that can retrieve value from the environment
 * @author Clement Bisaillon
 */
public interface Sensor {
	/**
	 * @return the value of the sensor
	 */
	double getValue();

	/**
	 * @return the data to save the sensor
	 */
	SensorData getData();
}
