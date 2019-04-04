package org.lrima.laop.plugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class PluginCreator {

    public static void createJar(String path, PluginActivator pluginActivator, ArrayList<Class> allClassesToLoad){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            JarOutputStream jarFile = new JarOutputStream(fileOutputStream);

            jarFile.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
            jarFile.write(getManifest(pluginActivator).getBytes());

            entryObjectClass(pluginActivator.getClass(), jarFile);
            loadClasses(allClassesToLoad, jarFile);

            jarFile.closeEntry();
            jarFile.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                "Activator: " +pluginActivator.getClass().getName()+"\n";
    }

    private static void entryObjectClass(Class<? extends PluginActivator> aClass, JarOutputStream outputStream) {
        try {
            outputStream.putNextEntry(new ZipEntry(getPath(aClass)));
            FileInputStream fileOutputStream = new FileInputStream(aClass.getProtectionDomain().getCodeSource().getLocation().getPath() + "\\" + aClass.getName().replace(".", "\\")+".class");
            int readBytes;
            byte[] buffer = new byte[4096];

            while((readBytes = fileOutputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, readBytes);
            }
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
