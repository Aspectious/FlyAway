package net.eastern.FlyAway.auth;

import java.time.ZonedDateTime;

public class AuthToken {
    private String ssid;
    private String code;
    private TokenStatus status;
    private ZonedDateTime creationDate;
    private ZonedDateTime expirationDate;
    private boolean isAdmin;
    public AuthToken() {

    }
}
