package org.lrima.laop.simulation.sensors;

import javafx.scene.paint.Color;
import org.lrima.laop.physic.concreteObjects.SimpleCar;
import org.lrima.laop.physic.staticobjects.StaticLineObject;
import org.lrima.laop.simulation.map.LineCollidable;
import org.lrima.laop.simulation.sensors.data.ProximityLineSensorData;
import org.lrima.laop.simulation.sensors.data.SensorData;
import org.lrima.laop.utils.MathUtils;
import org.lrima.laop.utils.math.Vector2d;

import java.awt.geom.Point2D;

/**
 * Sensor giving the distance from the car to the wall in a straight line
 * @author Clement Bisaillon
 */
public class ProximityLineSensor implements Sensor, LineCollidable {
	//The car that this sensor is attached to
	private SimpleCar car;
	private double orientation;
	public static final double SENSOR_LENGHT = 150;
	public static final double SENSOR_LENGHT_MINUS_1 = 1.0/SENSOR_LENGHT;
	private Point2D start;

	private final Color SENSOR_COLOR = new Color(255.0/255, 137.0/255, 132.0/255, 1);
    private double value;

    public ProximityLineSensor(SimpleCar car, double orientation) {
		this.car = car;
		this.orientation = orientation;
	}

	@Override
	public double getValue() {
		return this.value;
	}

	@Override
	public SensorData getData() {
		Vector2d center = car.getCenter();
		this.start = new Point2D.Double(center.getX(), center.getY());
		return new ProximityLineSensorData(this.start, this.orientation, SENSOR_LENGHT, value);
	}

    //OPTIMISATION

    @Override
    public void collide(StaticLineObject line) {
        float[] v = MathUtils.segmentIntersection(x1, y1, x2, y2, line.getX1(), line.getY1(), line.getX2(), line.getY2());

        if(v!=null){
            float x = v[0] - x1;
            float y = v[1] - y1;

            value = Math.min(Math.sqrt(x*x + y*y)*SENSOR_LENGHT_MINUS_1, value);
        }
    }

    float x1, x2, y1, y2;
    @Override
    public void bake() {
        this.value = 1;
        x1 = (float) this.car.getCenter().getX();
        y1 = (float) this.car.getCenter().getY();

        float dx = (float) (SENSOR_LENGHT * Math.cos(orientation + car.getRotation()));
        float dy = (float) (SENSOR_LENGHT * Math.sin(orientation + car.getRotation()));

        x2 = (float) (dx + this.car.getCenter().getX());
        y2 = (float) (dy + this.car.getCenter().getY());
    }

    @Override
    public float getX1() {
        return x1;
    }

    @Override
    public float getY1() {
        return y1;
    }

    @Override
    public float getX2() {
        return x2;
    }

    @Override
    public float getY2() {
        return y2;
    }
}
