package org.lrima.laop.utils;

public interface Action<T> {
    void handle(T value);
}
