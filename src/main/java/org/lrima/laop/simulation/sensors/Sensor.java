package org.lrima.laop.simulation.sensors;

import org.lrima.laop.ui.Drawable;

/**
 * Interface representing a sensor that can retrieve value from the environment
 * @author Clement Bisaillon
 */
public interface Sensor {
	double getValue();
	SensorData getData();
}
