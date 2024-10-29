package net.eastern.FlyAway.auth;

import java.time.ZonedDateTime;

public class AuthToken {
    private String ssid;
    private String code;
    private TokenStatus status;
    private ZonedDateTime creationDate;
    private ZonedDateTime expirationDate;
    private String owner;
    private boolean isAdmin;
    public AuthToken() {
        this.ssid="00000000-0000-0000-0000-000000000000";
        this.code="00000000-0000-0000-0000-000000000000";
        this.status = TokenStatus.INVALIDATED;
        this.creationDate=ZonedDateTime.now();
        this.expirationDate=ZonedDateTime.now();
        this.owner="nobody";
        this.isAdmin=false;
    }
    public AuthToken(String session, String Owner, boolean isAdmin) {
        this.ssid=session;
        this.code=java.util.UUID.randomUUID().toString();
        this.status = TokenStatus.VALIDATED;
        this.creationDate=ZonedDateTime.now();
        this.expirationDate=ZonedDateTime.now().plusDays(1);
        this.owner = Owner;
        this.isAdmin=isAdmin;
    }

    public String getssid() {
        return ssid;
    }
    public String getCode() {
        return code;
    }
    public String getOwner() {
        return owner;
    }
    public TokenStatus getStatus() {
        return status;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }
}
