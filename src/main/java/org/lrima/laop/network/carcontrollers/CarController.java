package org.lrima.laop.network.carcontrollers;

import org.lrima.laop.physic.CarControls;

/**
 *
 *
 */
public interface CarController {
    CarControls control(double ... captorValues);
}
