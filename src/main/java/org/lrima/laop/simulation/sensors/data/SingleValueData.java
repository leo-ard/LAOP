package org.lrima.laop.simulation.sensors.data;

import javafx.scene.canvas.GraphicsContext;
import org.lrima.laop.simulation.sensors.SensorData;
import org.lrima.laop.ui.Drawable;

public class SingleValueData implements SensorData {
    double value;

    public SingleValueData(double value) {
        this.value = value;
    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
