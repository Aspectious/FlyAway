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


    /*
        Methods for loading the res/pathmap.csv file to tell server what type of file is where
     */
    public static Map<String, String[]> map;

    public static Map<String, String[]> getFileMap() throws IOException {

        Map<String, String[]> map = new HashMap<String,String[]>();

        InputStream is = Utils.class.getResourceAsStream("/pathmap.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while (br.ready()){
            line = br.readLine();
            String[] arr = line.split(",");
            map.put(arr[1],new String[]{arr[2],arr[0]});

        }
        return map;
    }

    public static String getMimeType(String filetype) {
        Map<String,String> lookupDictionary = new HashMap<String,String>();
        lookupDictionary.put("jpeg","image/jpeg");
        lookupDictionary.put("jpg","image/jpeg");
        lookupDictionary.put("ico","image/vnd.microsoft.icon");
        lookupDictionary.put("","text/plain");
        lookupDictionary.put("txt","text/plain");
        lookupDictionary.put("html","text/html");
        lookupDictionary.put("htm","text/html");
        lookupDictionary.put("js","text/javascript");
        lookupDictionary.put("css","text/css");
        return lookupDictionary.get(filetype);
    }
    public static void loadMap() {
        try {
            map = Utils.getFileMap();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
    private static void checkIfFileExists(String url) throws FileNotFoundException {
        if (map.get(url) == null) {
            throw new FileNotFoundException("File not found");
        }
        try {
            map.get(url);
        } catch (NullPointerException e) {
            throw new FileNotFoundException("File not found");
        }
    }
    public static String getmimeTypeFromURL(String url) throws FileNotFoundException {
        checkIfFileExists(url);
        String item = map.get(url)[1];
        return getMimeType(item);
    }

    public static String getResPathFromURL(String url) throws FileNotFoundException {
        checkIfFileExists(url);
        return (String) map.get(url)[0];
    }
}
