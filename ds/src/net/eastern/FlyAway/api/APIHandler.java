package net.eastern.FlyAway.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.eastern.FlyAway.api.obj.Message;
import net.eastern.FlyAway.util.Utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @author aspectious
 * This basically handles all API Requests for us for the forseeable future. Yippee.
 * Kinda needed as one file as we do need to send a response at the end of the day.
 */
public class APIHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Utils.Infoprintln("New Request From " + exchange.getRemoteAddress() + " On Path " + exchange.getRequestURI());
        Utils.Infoprintln("Request Header: " + exchange.getRequestHeaders());
        InputStreamReader sr = new InputStreamReader(exchange.getRequestBody());
        String body = "";
        int r;
        while ((r = sr.read()) != -1) {
            body += (char) r;
        }
        Utils.Infoprintln("Request Body: " + body);


        String data = new Message("200 Okie dokie").getRawJSON();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin","*");
        exchange.sendResponseHeaders(200, data.length());
        OutputStream os = exchange.getResponseBody();
        os.write(data.toString().getBytes());
        os.close();
    }
}
