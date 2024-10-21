package net.eastern.FlyAway.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

public class RootHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

        // Print Debug info for request
        System.out.println("[Info] New Request, origin \"" + t.getRemoteAddress().getAddress() + ":" + t.getRemoteAddress().getPort() + "\", target \"" + t.getRequestURI() + "\"");
        int response;
        byte[] data;
        String filetype;
        try {
            InputStream filestr = getClass().getResourceAsStream("/wwwroot/html/index.html");
            filetype = URLConnection.guessContentTypeFromStream(filestr);
            data = filestr.readAllBytes();
            response = 200;
        }  catch (Exception e) {
            e.printStackTrace(System.err);
            data = new byte[0];
            filetype = "text/plain";
            response = 404;
        }
        t.setAttribute("Content-Type", filetype);
        t.sendResponseHeaders(response, data.length);
        OutputStream os = t.getResponseBody();
        os.write(data);
        os.close();
    }
}
