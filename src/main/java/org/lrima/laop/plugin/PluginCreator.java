package org.lrima.laop.plugin;

import org.lrima.laop.network.LearningAlgorithm;
import org.lrima.laop.utils.ClassUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/**
 * Utility class to create jar files that can later be imported in the platform
 *
 * @author Léonard
 */
public class PluginCreator {

    /**
     * Creates a jar file containing the main class <code>classLoad</code> and the dependencies <code>allClassesToLoad</code>
     *
     * @param path the path to export the jar file to
     * @param classLoad the main class to load
     * @param allClassesToLoad all other classes to include in the jar file
     * @throws IOException
     */
    public static void createJar(String path, Class classLoad, ArrayList<Class> allClassesToLoad) throws IOException {
        if(!path.endsWith(".jar"))
            path+=".jar";

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        JarOutputStream jarFile = new JarOutputStream(fileOutputStream);

        jarFile.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
        jarFile.write(getManifest(classLoad).getBytes());

        entryObjectClass(classLoad, jarFile);
        if(allClassesToLoad != null) loadClasses(allClassesToLoad, jarFile);

        jarFile.close();
        fileOutputStream.close();

    }

    private static void loadClasses(ArrayList<Class> allClassesToLoad, JarOutputStream jarFile) {
        for (Class aClass : allClassesToLoad) {
            entryObjectClass(aClass, jarFile);
        }
    }

    private static String getPath(Class c){
        return c.getName().replace(".", "/") + ".class";
    }

    private static String getManifest(Class learningAlgo) {
        if(ClassUtils.checkIfInterface(learningAlgo, PluginActivator.class)){
            return "Manifest-Version: 1.0\n"+
                    PluginLoader.ACTIVATOR_CLASS_TAG + ": " + learningAlgo.getName()+"\n";
        }
        if(ClassUtils.checkIfInterface(learningAlgo, LearningAlgorithm.class)){
            return "Manifest-Version: 1.0\n"+
                    PluginLoader.LEARNING_CLASS_TAG + ": " + learningAlgo.getName()+"\n";
        }

        System.err.println("Cannot export this kind of field");
        return null;

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
}
