package net.eastern.FlyAway.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class RootHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

        // Print Debug info for request
        System.out.println("[Info] New Request, origin \"" + t.getRemoteAddress().getAddress() + ":" + t.getRemoteAddress().getPort() + "\", target \"" + t.getRequestURI() + "\"");

        String response = "200 OK";
        t.sendResponseHeaders(200,response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
