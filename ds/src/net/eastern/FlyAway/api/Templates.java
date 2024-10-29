package net.eastern.FlyAway.api;

import net.eastern.FlyAway.auth.AuthToken;
import net.eastern.FlyAway.auth.TokenStatus;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;

public class Templates {
    static String generateAuthReturnJSON(String username, String ssid, AuthToken token) {
        String response;
        if (token.getStatus() != TokenStatus.VALIDATED) {
            response = "{\"AuthResponse\":{\"username\":\""
                    + username
                    + "\",\"sessionID\":\""
                    + ssid
                    + "\",\"result\":\"401\"}}";
        } else {
            response = "{\"AuthResponse\":{\"username\":\""
                    + username
                    + "\",\"sessionID\":\""
                    + ssid
                    + "\",\"result\":\"200\","
                    + "\"token\":{\"code\":\""
                    + token.getCode()
                    + "\",\"exp\":\""
                    + token.getExpirationDate().format(DateTimeFormatter.ISO_DATE)
                    +"\"}}}";
        }
        return response;
    }
}
