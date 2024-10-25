package net.eastern.FlyAway.web;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.eastern.FlyAway.util.DMBAPI;

import java.io.IOException;
import java.io.OutputStream;
import net.eastern.FlyAway.util.Utils;

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
            Utils.Infoprintln("Validating ID Number: " + idnum);
            DMBAPI input = new DMBAPI();

            int result;
            try {
                result = input.processCommand("sendrecord " + idnum);
            } catch (Exception e) {
                result = 500;
                e.printStackTrace();
            }
            String response;
            switch (result) {
                case 200:
                    response = "200 OK";
                    break;
                    case 403:
                        response = "403 Forbidden";
                        break;
                        default:
                            response = "500 Internal Server Error";
                            break;
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return;
        }

    }
}
