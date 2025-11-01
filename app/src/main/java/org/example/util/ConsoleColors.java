package org.example.util;

public class ConsoleColors {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";

    public static String colorize(String text, String color) {
        return color + text + RESET;
    }

    public static String green(String text) {
        return colorize(text, GREEN);
    }

    public static String red(String text) {
        return colorize(text, RED);
    }
}
