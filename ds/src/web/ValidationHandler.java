package net.eastern.FlyAway.web;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.eastern.FlyAway.DMBAPI;

import java.io.IOException;
import java.io.OutputStream;

public class ValidationHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        String request = t.getRequestURI().toString();

        if (!request.contains("?")) {
            String response = "400 MALFORMED REQUEST";
            t.sendResponseHeaders(400, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return;
        } else {
            String[] args = request.split("\\?");
            int idnum = Integer.parseInt(args[1].split("=")[1]);
            System.out.println();
            System.out.println(idnum);
            DMBAPI input = new DMBAPI();

            try {
                input.processCommand("sendrecord " + idnum);
            } catch (Exception e) {
                e.printStackTrace();
            }


            String response = "200 OK";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return;
        }

    }
}
