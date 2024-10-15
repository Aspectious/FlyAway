package net.eastern.FlyAway.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.eastern.FlyAway.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

        // Print Debug info for request
        System.out.println("[Info] New Request, origin \"" + t.getRemoteAddress().getAddress() + ":" + t.getRemoteAddress().getPort() + "\", target \"" + t.getRequestURI() + "\"");
        int response;
        byte[] data;
        String filetype;

        try {
            String path = Util.getResPathFromURL(t.getRequestURI() + "");
            InputStream filestr = getClass().getResourceAsStream(path);
            filetype = URLConnection.guessContentTypeFromStream(filestr);
            data = filestr.readAllBytes();
            response = 200;
        } catch (FileNotFoundException e) {
            data = "404 Not Found".getBytes();
            filetype = "text/plain";
            response = 404;
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
            data = "500 INTERNAL SERVER ERROR".getBytes();
            filetype = "text/plain";
            response = 500;
        }
        t.setAttribute("Content-Type", filetype);
        t.sendResponseHeaders(response, data.length);
        OutputStream os = t.getResponseBody();
        os.write(data);
        os.close();
    }
}
