package org.lrima.laop.simulation.sensors.data;

import java.awt.geom.Point2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.MatrixType;
import org.lrima.laop.simulation.sensors.SensorData;
import org.lrima.laop.ui.Drawable;

/**
 * The data to save the state of the proximity sensor
 * @author Clement Bisaillon
 */
public class ProximityLineSensorData implements SensorData {
	
	private double startX, startY;
	private double angle;
	private double length;
	private double value;
	
	/**
	 * Save the state of a proximity line sensor at a certain moment
	 * @param start the starting position of the sensor
	 * @param angle the angle of the sensor
	 * @param lenght the lenght of the sensor
	 */
	public ProximityLineSensorData(Point2D start, double angle, double lenght, double value) {
		this.startX = start.getX();
		this.startY = start.getY();
		this.angle = angle;
		this.length = lenght;
		this.value = value;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.getTransform().appendRotation(angle);
		
		double x2 = this.startX + Math.cos(this.angle) * this.length;
		double y2 = this.startY + Math.sin(this.angle) * this.length;
		
		gc.strokeLine(startX, startY, x2, y2);

		gc.fillText(String.format("%.2f", value), x2, y2);
		
		gc.getTransform().prependRotation(angle);
	}
}
