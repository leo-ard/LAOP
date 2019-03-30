package org.lrima.laop.network.nn;

public interface Layer {
    int size();
    public double[] feedFoward(double[] data);
}
