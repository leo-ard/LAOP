package org.lrima.laop.utils;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 * A console manager to print messages
 *
 * @author Léonard
 */
public class Console {
    public static ArrayList<BiConsumer<LogType, String>> listeners = new ArrayList<>();

    /**
     * Prints an error message
     * @param s the error string
     */
    public static void err(String s){
        warnListeners(LogType.ERR, s);
    }

    /**
     * Prints an error message
     * @param s the error string
     * @param args the arguments of the string {@link String#format(String, Object...)}
     */
    public static void err(String s, Object ... args){
        warnListeners(LogType.ERR, String.format(s, args));
    }

    /**
     * Prints an warning message
     * @param s the warning string
     */
    public static void warn(String s){
        warnListeners(LogType.WARN, s);
    }

    /**
     * Prints an warning message
     * @param s the warning string
     * @param args the arguments of the string {@link String#format(String, Object...)}
     */
    public static void warn(String s, Object ... args){
        warnListeners(LogType.WARN, String.format(s, args));
    }

    /**
     * Prints an info message
     * @param s the info string
     */
    public static void info(String s){
        warnListeners(LogType.INFO, s);
    }

    /**
     * Prints an info message
     * @param s the info string
     * @param args the arguments of the string {@link String#format(String, Object...)}
     */
    public static void info(String s, Object ... args){
        warnListeners(LogType.INFO, String.format(s, args));
    }

    /**
     * Adds a listener when a console method is invoked
     *
     * @param stringAction the action to do
     */
    public static void addListener(BiConsumer<LogType, String> stringAction){
        listeners.add(stringAction);
    }


    private static void warnListeners(LogType logtype, String s){
        listeners.forEach(c -> c.accept(logtype, s));
    }

    /**
     * Class containing all the types of messages
     */
    public enum LogType {
        INFO(Color.BLUE, "INFO"),
        WARN(Color.YELLOW, "WARN") ,
        ERR(Color.RED, "ERR");

        private String prefix;
        private Color color;
        LogType(Color color, String prefix){
            this.color = color;
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }

        public Color getColor() {
            return color;
        }
    }
}
