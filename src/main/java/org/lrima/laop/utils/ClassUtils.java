package org.lrima.laop.utils;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.concreteLearning.GeneticLearning;
import org.lrima.laop.network.concreteNetworks.NEAT;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtils {
    public static boolean checkIfInterface(Class c, Class i){
        if(c == i && c.isInterface()) return true;
        for (Class anInterface : c.getInterfaces()) {
            if(anInterface == i || checkIfInterface(anInterface, i)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfGenericOfInterface(Class c, Class i){
        Type[] genericInterfaces = c.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                for (Type genericType : genericTypes) {
                    if(checkIfInterface(i, (Class) genericType)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args){
        System.out.println(checkIfGenericOfInterface(GeneticLearning.class, NEAT.class));
    }
}
