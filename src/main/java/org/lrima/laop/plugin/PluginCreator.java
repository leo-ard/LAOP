package org.lrima.laop.plugin;

import org.lrima.laop.network.CarControllerAnotation;
import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.network.carcontrollers.CarController;
import org.lrima.laop.network.concreteNetworks.DrunkNetwork;
import org.lrima.laop.network.genetics.GeneticNeuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class PluginCreator {

    public static void createJar(String path, Class classLoad, ArrayList<Class> allClassesToLoad) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        JarOutputStream jarFile = new JarOutputStream(fileOutputStream);

        jarFile.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
//        jarFile.write(getManifest(classLoad));

        entryObjectClass(classLoad, jarFile);
        if(allClassesToLoad != null) loadClasses(allClassesToLoad, jarFile);

        jarFile.close();
        fileOutputStream.close();

    }

    private static void createJar(String path, Object object, ArrayList<Class> allClassesToLoad, byte[] manifest) throws IOException {

    }

    private static void loadClasses(ArrayList<Class> allClassesToLoad, JarOutputStream jarFile) {
        for (Class aClass : allClassesToLoad) {
            entryObjectClass(aClass, jarFile);
        }
    }

    private static String getPath(Class c){
        return c.getName().replace(".", "/") + ".class";
    }

    private static String getManifest(PluginActivator pluginActivator) {
        return "Manifest-Version: 1.0\n"+
                PluginLoader.Activator_CLASS_TAG + ": " +pluginActivator.getClass().getName()+"\n";
    }

    private static String getManifest(CarController carContoller) {
        return "Manifest-Version: 1.0\n"+
                PluginLoader.ALGORITHM_CLASS_TAG+ ": " +carContoller.getClass().getName()+"\n";
    }

    private static String getManifest(Class learningAlgo) {
        for (AnnotatedType annotatedInterface : learningAlgo.getAnnotatedInterfaces()) {
            System.out.println(annotatedInterface.getType());
        }


        return "Manifest-Version: 1.0\n"+
                PluginLoader.LEARNING_CLASS_TAG + ": " +learningAlgo.getClass().getName()+"\n";
    }

    private static void entryObjectClass(Class<?> aClass, JarOutputStream outputStream) {
        try {
            outputStream.putNextEntry(new ZipEntry(getPath(aClass)));
            FileInputStream fileOutputStream = new FileInputStream(aClass.getProtectionDomain().getCodeSource().getLocation().getPath() + "\\" + aClass.getName().replace(".", "\\")+".class");
            int readBytes;
            byte[] buffer = new byte[4096];

            while((readBytes = fileOutputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, readBytes);
            }
            outputStream.closeEntry();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        System.out.println(DrunkNetwork.class.isAssignableFrom(GeneticNeuralNetwork.class));
    }
}
