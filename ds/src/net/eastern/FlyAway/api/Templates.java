package net.eastern.FlyAway.api;

import net.eastern.FlyAway.auth.AuthToken;
import net.eastern.FlyAway.auth.TokenStatus;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;

/**
 * This Class is used to help build returning JSON Responses in an easy-to-use multi-situational way
 * Life is pain.
 * @author aspectious
 */
public class Templates {

    /**
     * AuthResponse JSON Template Builder.
     * @param username
     * @param ssid
     * @param token
     * @return
     */
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
    static String generateValidationResponseJSON(boolean validated) {
        String response;
        if (validated) {
            response = "{\"ValidationResponse\":{\"result\":\"200\"}}";
        } else {
            response = "{\"ValidationResponse\":{\"result\":\"401\"}}";
        }
        return response;
    }
}
