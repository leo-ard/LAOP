package org.lrima.laop.plugin;


import org.lrima.laop.core.LAOP;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.Manifest;

public class PluginLoader {
    private static URLClassLoader classLoader;
    private static ArrayList<URL> jars = new ArrayList<>();

    public static String ALGORITHM_CLASS_TAG = "Algorithm-Class";
    public static String LEARNING_CLASS_TAG = "Learning-Class";
    public static String ACTIVATOR_CLASS_TAG = "Activator";

    public static void load(LAOP laop) throws IOException {
        URL[] urls = new URL[jars.size()];
        for (int i = 0; i < jars.size(); i++) {
            urls[i] = jars.get(i);
        }
        classLoader = URLClassLoader.newInstance(urls);

        for(URL path : jars) {
            System.out.println(path);
            JarURLConnection con = (JarURLConnection) path.openConnection();
            try {
                loadManifest(con.getManifest(), laop);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        jars = new ArrayList<>();
    }

    private static void loadManifest(Manifest manifest, LAOP laop) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(manifest.getMainAttributes().getValue(ACTIVATOR_CLASS_TAG) != null){
            Class activatorClass = classLoader.loadClass(manifest.getMainAttributes().getValue(ACTIVATOR_CLASS_TAG));
            PluginActivator activator = (PluginActivator) activatorClass.newInstance();
            activator.initiate(laop);
        }
        if(manifest.getMainAttributes().getValue(ALGORITHM_CLASS_TAG) != null){
            Class allgorithmClass = classLoader.loadClass(manifest.getMainAttributes().getValue(ALGORITHM_CLASS_TAG));
            laop.getNeuralNetworksClasses().add(allgorithmClass);
        }
        if(manifest.getMainAttributes().getValue(LEARNING_CLASS_TAG) != null){
            Class allgorithmClass = classLoader.loadClass(manifest.getMainAttributes().getValue(LEARNING_CLASS_TAG));
            laop.getNeuralNetworksClasses().add(allgorithmClass);
        }
    }




    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        ArrayList<Class> classes = new ArrayList<>();
//        classes.add(Test.class);
//        PluginCreator.createJar("helpme.jar", new AlgorithmActivator(), classes);

        addDir("algos/");
        LAOP laop = new LAOP();
        load(laop);

    }

    public static void addJar(String path){
        try {
            jars.add(new URL("jar:file:"+path+"!/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void addDir(String path){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if(file.isFile() && file.getName().endsWith(".jar")){
                addJar(file.getPath());
            }
        }
    }
}
