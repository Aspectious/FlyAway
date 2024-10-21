package net.eastern.FlyAway.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Utils {
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
        System.out.println(ANSI_GRAY + getNow() + "[Debug] [" + StackWalker.getInstance().walk(stream -> stream.skip(1).findFirst().get()).getClassName() + "] " + message + ANSI_RESET );
    }

    public static final void Infoprintln(String message) {
        System.out.println(ANSI_RESET + getNow() + "[Info] [" +  StackWalker.getInstance().walk(stream -> stream.skip(1).findFirst().get()).getClassName() + "] " + message + ANSI_RESET);
    }
    public static final void Warnprintln(String message) {
        System.out.println(ANSI_YELLOW + getNow() + "[Warn] [" +  StackWalker.getInstance().walk(stream -> stream.skip(1).findFirst().get()).getClassName() + "] " + message + ANSI_RESET);
    }
    public static final void Errprintln(String message) {
        System.err.println(ANSI_YELLOW + getNow() + "[Warn] [" +  StackWalker.getInstance().walk(stream -> stream.skip(1).findFirst().get()).getClassName() + "] " + message + ANSI_RESET);
    }
    public static Map<String,String> map;

    public static Map<String, String> getFileMap() throws IOException {

        Map<String,String> map = new HashMap<String,String>();
        //map.put("/","/wwwroot/html/index.html");
        InputStream is = Utils.class.getResourceAsStream("/pathmap.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while (br.ready()){
            line = br.readLine();
            String[] arr = line.split(",");
            map.put(arr[0],arr[1]);

        }
        return map;
    }
    public static void loadMap() {
        try {
            map = Utils.getFileMap();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
    public static String getResPathFromURL(String url) throws FileNotFoundException {
        if (map.get(url) == null) {
            throw new FileNotFoundException("File not found");
        }
        try {
            map.get(url);
        } catch (NullPointerException e) {
            throw new FileNotFoundException("File not found");
        }
        return map.get(url);
    }
}
