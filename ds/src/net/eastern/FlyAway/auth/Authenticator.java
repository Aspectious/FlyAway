package net.eastern.FlyAway.auth;

import net.eastern.FlyAway.util.DBAPI;

import java.sql.SQLException;

/**
 * This Class's Purpose is to handle User Authentication and Token Issuance with the DBM and the API.
 * @author aspectious
 */
public class Authenticator {

    /**
     * Haha User Auth go brrr
     * @param Username
     * @param pwdhash
     * @param ssid
     * @return
     */
    public static AuthToken Authenticate_User(String Username, String pwdhash, String ssid) {
        User usr;
        AuthToken token;
            usr = new DBAPI().fetchUserByUsername(Username);
            if (!usr.getPasswordhash().equals(pwdhash)) {
                return new AuthToken();
            }
            boolean isadm;
            if (usr.getPermsum() == 777) isadm = true;
            else isadm = false;
            token = new AuthToken(ssid,Username,isadm);
            new DBAPI().addToken(token);
        return token;
    }
    public static boolean CheckToken(String code) {
        AuthToken token = new DBAPI().fetchToken(code);
        if (token.getStatus() == TokenStatus.VALIDATED) {
            return true;
        } else return false;
    }
}
