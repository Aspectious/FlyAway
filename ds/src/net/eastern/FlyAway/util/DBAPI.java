package net.eastern.FlyAway.util;

import net.eastern.FlyAway.auth.AuthToken;
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
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Objects;

public class DBAPI {
    /**
     * Grabs a User Object from the Database.
     * @param username
     * @return
     * @throws SQLException
     */
    public User fetchUserByUsername(String username) throws SQLException {
        User usr;
        Dbm dbm = new Dbm();
        Connection conn = dbm.getConnection();
        try {
            DbmResponse resp = dbm.executeSQL(conn,DbmQueryType.QUERY,"SELECT * FROM flyawaydev.accts WHERE un=\""+ username +"\";");
            String[] userinfo = resp.getContentArray();
            if (Objects.equals(userinfo[5], "null")) {
                usr = new User(userinfo[1], userinfo[2], Integer.parseInt(userinfo[3]), LocalDateTime.parse(userinfo[4].replace(" ", "T")));
                } else {
                usr = new User(userinfo[1], userinfo[2], Integer.parseInt(userinfo[3]), LocalDateTime.parse(userinfo[4].replace(" ", "T")), LocalDateTime.parse(userinfo[5].replace(" ","T")));
            }
        } catch (SQLException ex) {
            throw ex;
        }
        return usr;
    }


    /**
     * Adds a token entry into the database.
     * @param token
     * @throws SQLException
     */
    public void addToken(AuthToken token) throws SQLException {
        String status;
        int isAdmin;
        if (token.getStatus() == TokenStatus.VALIDATED) {
            status = "VALIDATED";
        } else status="INVALIDATED";
        if (token.isAdmin()) isAdmin = 1; else isAdmin = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdate = token.getCreationDate().format(formatter);
        String expdate = token.getExpirationDate().format(formatter);

        Dbm dbm = new Dbm();
        Connection conn = dbm.getConnection();
        try {
            dbm.executeSQL(conn,DbmQueryType.UPDATE,
                    "INSERT INTO flyawaydev.tokens (token, status, admin, sessionid, creationdate, expdate)" +
                            "VALUES ('" + token.getCode()+ "' , '"+ status + "', " + isAdmin + ", '"+token.getssid()+ "', '" + createdate + "', '" + expdate +"');");
        } catch (SQLException ex) {
            throw ex;
        }
    }







    /**
     * DEPRECATED CODE, DO NOT USE
     * @deprecated
     * @param fullcommand
     * @return
     * @throws SQLException
     */
    public int processCommand(String fullcommand) throws SQLException {

        int result;

        String[] brokencmd = fullcommand.toLowerCase().split(" ");
        String[] args = new String[brokencmd.length - 1];
        System.arraycopy(brokencmd, 1, args, 0, brokencmd.length - 1);
        String command = brokencmd[0];
        Utils.Debugprintln("[Input] Running command: " + fullcommand);
        try {
            String earlyexit;
            switch (command) {
                case "":
                    result = 500;
                    break;
                case "sendrecord":
                    Dbm dbm = new Dbm();
                    LocalDateTime dt = LocalDateTime.now();
                    // For Testing
                    Connection conn = dbm.getConnection();
                    dbm.setConnection(conn);
                    boolean exitallowed = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT exitallowed FROM users WHERE studentid = " + args[0]).getContentArray()[0].equals("1");
                    if (exitallowed) {
                        earlyexit = "APPROVED";
                        result = 200;
                    } else {
                        earlyexit = "REJECTED";
                        result = 403;
                    }
                    DbmResponse userexistsresponse = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT studentid FROM users WHERE studentid = " + args[0]);
                    if (userexistsresponse.getType() == DbmResponseType.ResponseEmpty) {
                        Utils.Debugprintln("User does not exist");
                        Utils.Debugprintln("Adding user to database");
                        String sql = "INSERT INTO users (studentid, exitallowed) VALUES (?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, Integer.parseInt(args[0]));
                        pstmt.setBoolean(2, false);
                        pstmt.executeUpdate();
                    } else if (userexistsresponse.getType() == DbmResponseType.ResponseList) {
                    }

                    DbmResponse response = dbm.executeSQL(conn, DbmQueryType.UPDATE, ("INSERT INTO records(sid, timestamp, result) VALUES (" + args[0] + ", '" + dt + "', '" + earlyexit + "')"));
                    if (response.getType() == DbmResponseType.OneResponse) {
                        System.out.println(response.getContentArray()[0]);
                        break;
                    }
                    break;
                case "setuserallow":
                    dbm = new Dbm();
                    conn = dbm.getConnection();
                    DbmResponse userfound = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT studentid FROM users WHERE studentid = " + args[0]);
                    if (userfound.getType() == DbmResponseType.ResponseEmpty) {
                        System.out.println("User does not exist");
                        System.out.println("Adding user to database");
                        String sql = "INSERT INTO users (studentid, exitallowed) VALUES (?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, Integer.parseInt(args[0]));
                        pstmt.setBoolean(2, false);
                        pstmt.executeUpdate();
                    }
                    System.out.println(String.join(" ", args));
                    boolean isint2 = false;
                    while (!isint2) {
                        try {
                            isint2 = true;
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid number");
                        }
                    }
                    try {
                        dbm = new Dbm();
                        conn = dbm.getConnection();
                        DbmResponse updated = dbm.executeSQL(conn, DbmQueryType.UPDATE, "UPDATE users SET exitallowed = " + args[1] + " WHERE studentid = " + args[0]);
                        System.out.println("User updated");
                    } catch (SQLException e) {
                        System.err.println("Error: " + e);
                    }
                    result = 500;
                    break;
                default:
                    result = 500;
                    System.err.println("[Input] Unknown command: " + command);
                    break;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-323138);
            return 500;
        }
    }
}