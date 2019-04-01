package org.lrima.laop.network.nn;

/**
 * Interface to create multiple types of layers.
 *
 * @author Léonard
 */
public interface Layer {

    /**
     * The size of the layer (number of neurons) to know the size of the data when using feedFoward
     *
     * @return the size of the layer
     */
    int size();

    /**
     * Calculated the feedFoward according of the inputs <code>data</code>
     *
     * @param data the inputs
     * @return the new data
     */
    public double[] feedFoward(double[] data);
}
