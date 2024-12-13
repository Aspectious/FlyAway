package net.eastern.FlyAway.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.eastern.FlyAway.auth.AuthToken;
import net.eastern.FlyAway.auth.Authenticator;
import net.eastern.FlyAway.auth.TokenStatus;
import net.eastern.FlyAway.cli.Input;
import net.eastern.FlyAway.util.DBAPI;
import net.eastern.FlyAway.util.Utils;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author aspectious
 * This basically handles all API Requests for us for the forseeable future. Yippee.
 * Kinda needed as one file as we do need to send a response at the end of the day.
 */
public class APIHandler implements HttpHandler {
    /**
     * Handles Everything JSON-API-HTTP-related because we are cool
     * @param exchange the exchange containing the request from the client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        /*
         * This is a bit complex but you know what it allows for CORS Requests so whatever
         */
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
            System.out.println("send help,pray our code compiles");


            /*
             * Now is the time where we actually parse the JSON Api Requests and Respond to them
             */

            // TODO: FIX FOR PRODUCTION, TEMPORARY FIX
            String[] wtf = body.split("\"");
            for (String str : wtf) {
                System.out.println(str);
                if (wtf[1] == "sendrecord"); {
                    Utils.Infoprintln("SENDRECORD FROM [" + exchange.getRemoteAddress() + "]: " + wtf[3]);
                    String record = wtf[3];
                    System.out.println(record);
                    new DBAPI().checkBadge(Integer.parseInt(record));
                    data = "{\"message\":\"200 ok\"}";//Templates.generateValidationResponseJSON(true);
                    System.out.println("Bro is validated");
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, data.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(data.toString().getBytes());
                    os.close();
                }
            }




            // TODO: WORKING CODE THAT IS JUST NOT IN USE
            /*
            try {

                JSONObject obj = new JSONObject(body); // Parses the JSON From the Body
                System.out.println(body);
                System.out.println(obj.toString());
                System.out.println(obj.isEmpty());
                System.out.println(obj.has("senderecord"));
                // Login Handler and Token Issuer, Yippee
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

                 // Validates Token's Authenticity

                if (obj.has("ValidateToken")) {
                    JSONObject token = obj.getJSONObject("ValidateToken");
                    String code = token.getString("token");
                    String ssid = token.getString("sessionID");
                    System.out.println(code);
                    System.out.println(ssid);
                    try {
                        AuthToken dbtoken = new DBAPI().fetchToken(code);
                        System.out.println(dbtoken.getCode());
                        System.out.println(dbtoken.getssid());
                        if (Objects.equals(ssid, dbtoken.getssid())) {
                            data = Templates.generateValidationResponseJSON(true);
                            System.out.println("Bro is validated");
                            exchange.getResponseHeaders().set("Content-Type", "application/json");
                            exchange.sendResponseHeaders(200, data.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(data.toString().getBytes());
                            os.close();
                        } else {
                            data = Templates.generateValidationResponseJSON(false);
                            exchange.getResponseHeaders().set("Content-Type", "application/json");
                            exchange.sendResponseHeaders(401, data.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(data.toString().getBytes());
                            os.close();
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        data = Templates.generateValidationResponseJSON(false);
                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.sendResponseHeaders(401, data.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(data.toString().getBytes());
                        os.close();
                    }

                }

                if(obj.has("sendrecord")) {
                    Utils.Infoprintln("SENDRECORD FROM [" + exchange.getRemoteAddress() + "]: " + obj.getString("sendrecord"));
                    String record = obj.getString("sendrecord");
                    System.out.println(record);
                    new DBAPI().checkBadge(Integer.parseInt(record));
                    data = "{\"message\":\"200 ok\"}";//Templates.generateValidationResponseJSON(true);
                    System.out.println("Bro is validated");
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, data.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(data.toString().getBytes());
                    os.close();

                }

                 // Sends a message to the server backend.

                if (obj.has("Message")) {
                    Utils.Infoprintln("MESSAGE FROM [" + exchange.getRemoteAddress() + "]: " + obj.getString("Message"));
                }

==

            } catch (Exception e) {


                 // If something goes wrong while parsing JSON, the syntax is most likely bad, blame it on the client.

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
        */


            /*
             * Send the Actual Client Data
             */
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            data = "{\"message\":\"200 OK\"}";
            exchange.sendResponseHeaders(200, data.length());
            OutputStream os = exchange.getResponseBody();
            os.write(data.toString().getBytes());
            os.close();
        } else {

            /*
             * Used for CORS Requests. DO NOT MODIFY, MUST HAVE EMPTY BODY WITH 204 CODE
             */
            data = "";
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers","Content-Type");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods","POST");
            exchange.sendResponseHeaders(204, 0);
            exchange.getResponseBody().close();
        }
    }
}
