package net.eastern.FlyAway;

import net.eastern.FlyAway.CLI.ShUtils;
import net.eastern.FlyAway.dbm.Dbm;
import net.eastern.FlyAway.dbm.DbmQueryType;
import net.eastern.FlyAway.dbm.DbmResponse;
import net.eastern.FlyAway.dbm.DbmResponseType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

public class DMBAPI {
    public void processCommand(String fullcommand) throws SQLException {
        String[] brokencmd = fullcommand.toLowerCase().split(" ");
        String[] args = new String[brokencmd.length - 1];
        System.arraycopy(brokencmd, 1, args, 0, brokencmd.length - 1);
        String command = brokencmd[0];
        ShUtils.Debugprintln("[Input] Running command: " + fullcommand);
        try {
            String earlyexit;
            switch (command) {
                case "":
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
                    } else {
                        earlyexit = "REJECTED";
                    }
                    DbmResponse userexistsresponse = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT studentid FROM users WHERE studentid = " + args[0]);
                    if (userexistsresponse.getType() == DbmResponseType.ResponseEmpty) {
                        ShUtils.Debugprintln("User does not exist");
                        ShUtils.Debugprintln("Adding user to database");
                        String sql = "INSERT INTO users (studentid, exitallowed) VALUES (?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, Integer.parseInt(args[0]));
                        pstmt.setBoolean(2, false);
                        pstmt.executeUpdate();
                    } else if (userexistsresponse.getType() == DbmResponseType.ResponseList) {
                    }

                    DbmResponse response = dbm.executeSQL(conn, DbmQueryType.UPDATE, ("INSERT INTO RECORDS (sid, timestamp, result) VALUES (" + args[0] + ", '" + dt + "', '" + earlyexit + "')"));
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
                    break;
                default:
                    System.err.println("[Input] Unknown command: " + command);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-323138);
        }
    }
}
