package net.eastern.FlyAway;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Util {
    public static Map<String,String> map;

    public static Map<String, String> getFileMap() throws IOException {

        Map<String,String> map = new HashMap<String,String>();
        map.put("/","/wwwroot/html/index.html");
        InputStream is = Util.class.getResourceAsStream("/pathmap.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while (br.ready()){
            line = br.readLine();
            String[] arr = line.split(",");
            System.out.println(arr[0]);
            System.out.println(arr[1]);
            map.put(arr[0],arr[1]);

        }
        return map;
    }
    public static void loadMap() {
        try {
            map = Util.getFileMap();
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
