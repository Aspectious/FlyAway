package net.eastern.FlyAway.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class QueryHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

        String response = "501 NOT IMPLEMENTED";
        t.sendResponseHeaders(501, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
