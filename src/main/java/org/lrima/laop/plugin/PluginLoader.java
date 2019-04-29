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

/**
 * Utility class to load jar files into the platform
 *
 * @author Léonard
 */
public class PluginLoader {
    private static URLClassLoader classLoader;
    private static ArrayList<URL> jars = new ArrayList<>();

    static String LEARNING_CLASS_TAG = "Learning-Class";
    static String ACTIVATOR_CLASS_TAG = "Activator";

    /**
     * Load the plugin activator with the LAOP class
     *
     * @param laop the laop instance to load the plugins to
     * @throws IOException
     */
    public static void load(LAOP laop) throws IOException {
        URL[] urls = new URL[jars.size()];
        for (int i = 0; i < jars.size(); i++) {
            urls[i] = jars.get(i);
        }
        classLoader = URLClassLoader.newInstance(urls);

        for(URL path : jars) {
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
        if(manifest.getMainAttributes().getValue(LEARNING_CLASS_TAG) != null){
            Class allgorithmClass = classLoader.loadClass(manifest.getMainAttributes().getValue(LEARNING_CLASS_TAG));
            laop.getLearningAlgorithmsClasses().add(allgorithmClass);
        }
    }

    /**
     * Add a jar to the path of jars to load
     *
     * @param path the path to the directory
     */
    public static void addJar(String path){
        try {
            jars.add(new URL("jar:file:"+path+"!/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a directory to the path of jars to load
     *
     * @param path the path to the directory
     */
    public static void addDir(String path){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if(file.isFile() && file.getName().endsWith(".jar")){
                    addJar(file.getPath());
                }
            }
        }
    }
}
