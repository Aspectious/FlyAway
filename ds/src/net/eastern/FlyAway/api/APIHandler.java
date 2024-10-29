package net.eastern.FlyAway.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.eastern.FlyAway.auth.AuthToken;
import net.eastern.FlyAway.auth.Authenticator;
import net.eastern.FlyAway.auth.TokenStatus;
import net.eastern.FlyAway.util.Utils;
import org.json.JSONObject;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;

/**
 * @author aspectious
 * This basically handles all API Requests for us for the forseeable future. Yippee.
 * Kinda needed as one file as we do need to send a response at the end of the day.
 */
public class APIHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String data;
        if (!exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            Utils.Infoprintln("New Request From " + exchange.getRemoteAddress() + " On Path " + exchange.getRequestURI());
            Utils.Infoprintln("Request Header: " + exchange.getRequestHeaders());
            InputStreamReader sr = new InputStreamReader(exchange.getRequestBody());
            String body = "";
            int r;
            while ((r = sr.read()) != -1) {
                body += (char) r;
            }
            Utils.Infoprintln("Request Body: " + body);

            try {
                JSONObject obj = new JSONObject(body);
                if (obj.has("LoginRequest")) {
                    String username,passwordhash,sessionid;
                    username = obj.getJSONObject("LoginRequest").getString("username");
                    passwordhash = obj.getJSONObject("LoginRequest").getString("password");
                    sessionid = obj.getJSONObject("LoginRequest").getString("sessionID");

                    System.out.println(username);
                    System.out.println(passwordhash);
                    System.out.println(sessionid);
                    AuthToken token = Authenticator.Authenticate_User(username,passwordhash,sessionid);

                    if (token.getStatus() == TokenStatus.VALIDATED) {
                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
                        data = Templates.generateAuthReturnJSON(username,sessionid,token);
                        exchange.sendResponseHeaders(200, data.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(data.toString().getBytes());
                        os.close();
                    } else {
                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
                        data = Templates.generateAuthReturnJSON(username,sessionid,token);
                        exchange.sendResponseHeaders(401, data.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(data.toString().getBytes());
                        os.close();
                    }

                }
                if (obj.has("ValidateToken")) {
                    String token = obj.getString("ValidateToken");

                }
                if (obj.has("Message")) {
                    Utils.Infoprintln("MESSAGE FROM [" + exchange.getRemoteAddress() + "]: " + obj.getString("Message"));
                }
                for (String key : obj.keySet()) {
                    System.out.println(obj.has("LoginRequest"));
                    System.out.println(key + ":" + obj.get(key));
                }

            } catch (Exception e) {
                System.err.println(e);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
                data = "{\"message\":\"400 MALFORMED REQUEST\"}";
                exchange.sendResponseHeaders(400, data.length());
                OutputStream os = exchange.getResponseBody();
                os.write(data.toString().getBytes());
                os.close();
                return;
            }




            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            data = "{\"message\":\"200 OK\"}";
            exchange.sendResponseHeaders(200, data.length());
            OutputStream os = exchange.getResponseBody();
            os.write(data.toString().getBytes());
            os.close();
        } else {
            data = "";
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers","Content-Type");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods","POST");
            exchange.sendResponseHeaders(204, 0);
            exchange.getResponseBody().close();
        }
    }
}
