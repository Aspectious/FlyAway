package net.eastern.FlyAway.CLI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShUtils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_GRAY = "\u001B[90m";

    private static final String getNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formNow = "[" + now.format(formatter) + "] ";
        return formNow;
    }

    public static final void Debugprintln(String message) {
        System.out.println(ANSI_GRAY + getNow() + "[Debug] " + message + ANSI_RESET);
    }

    public static final void Infoprintln(String message) {
        System.out.println(ANSI_RESET + getNow() + "[Info] " + message + ANSI_RESET);
    }
}
