package net.eastern.FlyAway.auth;

import net.eastern.FlyAway.util.DMBAPI;

import java.sql.SQLException;

/**
 * @
 */
public class Authenticator {
    public static AuthToken Authenticate_User(String Username, String pwdhash, String ssid) {
        User usr;
        AuthToken token;
        try {
            usr = new DMBAPI().fetchUserByUsername(Username);
            if (!usr.getPasswordhash().equals(pwdhash)) {
                return new AuthToken();
            }
            boolean isadm;
            if (usr.getPermsum() == 777) isadm = true;
            else isadm = false;
            token = new AuthToken(ssid,Username,isadm);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            new DMBAPI().addToken(token);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return token;
    }
}
