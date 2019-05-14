package org.lrima.laop.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Utility class for reflection-type queries
 *
 * @author Leonard
 */
public class ClassUtils {
    /**
     * Checks if a class <code>c</code> implements a certain interface <code>i</code>
     *
     * @param c the class
     * @param i the interface
     * @return true if the class implemets the class. False otherwise
     */
    public static boolean checkIfInterface(Class c, Class i){
        if(c == i && c.isInterface()) return true;
        for (Class anInterface : c.getInterfaces()) {
            if(anInterface == i || checkIfInterface(anInterface, i)){
                return true;
            }
        }
        return false;
    }
}
