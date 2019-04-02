package org.lrima.laop.plugin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class PluginLoader {

    public static Class<?> loadPlugin(String path){




        return null;
    }

    public static void createJar(String path){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            JarOutputStream jarFile = new JarOutputStream(fileOutputStream);

            jarFile.putNextEntry(new ZipEntry("com/help"));
            jarFile.putNextEntry(new ZipEntry("com/help/model.txt"));


            jarFile.closeEntry();
            jarFile.close();
            fileOutputStream.close();
            fileOutputStream.close();


//            jarFile.putNextEntry(new ZipEntry());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        createJar("helpme.jar");
    }
}
