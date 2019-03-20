package org.lrima.laop.utils;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Console {
    public static ArrayList<BiConsumer<LogType, String>> listeners;

    public static void out(String s){
        warnListeners(LogType.OUT, s);
    }

    public static void err(String s){
        warnListeners(LogType.ERR, s);
    }

    public static void warn(String s){
        warnListeners(LogType.WARN, s);
    }

    public static void info(String s){
        warnListeners(LogType.INFO, s);
    }

    public static void addListener(BiConsumer<LogType, String> stringAction){
        listeners.add(stringAction);
    }

    private static void warnListeners(LogType logtype, String s){
        listeners.forEach(c -> c.accept(logtype, s));
        System.out.printf("[%s] %s", logtype.getPrefix(), s);
    }

    enum LogType {
        INFO(Color.BLUE, "INFO"),
        WARN(Color.YELLOW, "WARN") ,
        ERR(Color.RED, "ERR"),
        OUT(Color.WHITE, "");

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
