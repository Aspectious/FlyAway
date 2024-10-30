package net.eastern.FlyAway.util;

import net.eastern.FlyAway.auth.AuthToken;
import net.eastern.FlyAway.auth.Authenticator;
import net.eastern.FlyAway.auth.TokenStatus;
import net.eastern.FlyAway.auth.User;
import net.eastern.FlyAway.dbm.Dbm;
import net.eastern.FlyAway.dbm.DbmQueryType;
import net.eastern.FlyAway.dbm.DbmResponse;
import net.eastern.FlyAway.dbm.DbmResponseType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DBAPI {
    /**
     * Grabs a User Object from the Database.
     * @param username String
     * @return User
     */
    public User fetchUserByUsername(String username) {
        User usr;
        Dbm dbm = new Dbm();
        try {
            Connection conn = dbm.getConnection();
            DbmResponse resp = dbm.executeSQL(conn,DbmQueryType.QUERY,"SELECT * FROM flyawaydev.accts WHERE un=\""+ username +"\";");
            String[] userinfo = resp.getContentArray();
            if (Objects.equals(userinfo[5], "null")) {
                usr = new User(userinfo[1], userinfo[2], Integer.parseInt(userinfo[3]), LocalDateTime.parse(userinfo[4].replace(" ", "T")));
                } else {
                usr = new User(userinfo[1], userinfo[2], Integer.parseInt(userinfo[3]), LocalDateTime.parse(userinfo[4].replace(" ", "T")), LocalDateTime.parse(userinfo[5].replace(" ","T")));
            }
        } catch (SQLException ex) {
            Utils.Errprintln(ex.getMessage());
            usr = new User();
        }
        return usr;
    }


    /**
     * Adds a token entry into the database.
     * @param token AuthToken
     */
    public void addToken(AuthToken token) {
        String status;
        int isAdmin;
        if (token.getStatus() == TokenStatus.VALIDATED) {
            status = "VALIDATED";
        } else status="INVALIDATED";
        if (token.isAdmin()) isAdmin = 1; else isAdmin = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdate = token.getCreationDate().format(formatter);
        String expdate = token.getExpirationDate().format(formatter);

        try {
            Dbm dbm = new Dbm();
            Connection conn = dbm.getConnection();
            dbm.executeSQL(conn,DbmQueryType.UPDATE,
                    "INSERT INTO flyawaydev.tokens (token, status, admin, sessionid, creationdate, expdate)" +
                            "VALUES ('" + token.getCode()+ "' , '"+ status + "', " + isAdmin + ", '"+token.getssid()+ "', '" + createdate + "', '" + expdate +"');");
        } catch (SQLException ex) {
            Utils.Errprintln(ex.getMessage());
        }
    }
    /**
     * Fetches a token from the database
     * @param tokencode String
     * @return AuthToken
     */
    public AuthToken fetchToken(String tokencode)  {
        try {
            Dbm dbm = new Dbm();
            Connection conn = dbm.getConnection();
            DbmResponse resp = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT * FROM flyawaydev.tokens WHERE token=\"" + tokencode + "\";");
            String code = resp.getContentArray()[1];
            TokenStatus status;
            if (resp.getContentArray()[2].equals("VALIDATED")) {
                status = TokenStatus.VALIDATED;
            } else {
                status = TokenStatus.INVALIDATED;
            }
            boolean isAdmin;
            isAdmin = resp.getContentArray()[3].equals("1");
            String sessionid = resp.getContentArray()[4];
            ZonedDateTime cdt;
            ZonedDateTime edt;

            AuthToken token;
            if (resp.getContentArray()[6].equals("null")) {

                cdt = LocalDateTime.parse(resp.getContentArray()[5]).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.systemDefault());
                token = new AuthToken(sessionid, code, "SYSTEM", status, isAdmin, cdt);
            } else {

                cdt = LocalDateTime.parse(resp.getContentArray()[5]).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.systemDefault());

                edt = LocalDateTime.parse(resp.getContentArray()[6]).atOffset(ZoneOffset.UTC).atZoneSameInstant(ZoneId.systemDefault());
                token = new AuthToken(sessionid, code, "SYSTEM", status, isAdmin, cdt, edt);
            }
            return token;
        } catch (SQLException e) {
            return new AuthToken();
        }
    }

    /**
     * Checks the badge with the database with an Authentication Token. Token must be validated.
     * @param idnum int
     * @param authToken String
     */
    public boolean checkBadge(int idnum, String authToken) {
        boolean tokenstatus = Authenticator.CheckToken(authToken);
        if (!tokenstatus) return false;
        try {
            Dbm dbm = new Dbm();
            LocalDateTime dt = LocalDateTime.now();
            Connection conn = dbm.getConnection();
            dbm.setConnection(conn);
            boolean exitallowed = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT exitallowed FROM users WHERE studentid = " + idnum).getContentArray()[0].equals("1");
            String earlyexit;
            if (exitallowed) {
                earlyexit = "APPROVED";
            } else {
                earlyexit = "REJECTED";
            }
            DbmResponse userexistsresponse = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT studentid FROM users WHERE studentid = " + idnum);
            if (userexistsresponse.getType() == DbmResponseType.ResponseEmpty) {
                System.out.println("User does not exist");
                System.out.println("Adding user to database");
                String sql = "INSERT INTO users (studentid, exitallowed) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, idnum);
                pstmt.setBoolean(2, false);
                pstmt.executeUpdate();
            }

            DbmResponse response = dbm.executeSQL(conn, DbmQueryType.UPDATE, ("INSERT INTO RECORDS (sid, timestamp, result) VALUES (" + idnum + ", '" + dt + "', '" + earlyexit + "')"));
            if (response.getType() == DbmResponseType.OneResponse) {
                System.out.println(response.getContentArray()[0]);
            }
            return exitallowed;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
