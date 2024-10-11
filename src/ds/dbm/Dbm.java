package net.eastern.FlyAway.dbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Dbm {
    private Connection dbconn;
    public Dbm() {
        // For Testing
        attemptConnection("jdbc:mysql://75.18.104.248:3306/flyawaydev", "aspectious",("p@ssw0rd123"));
    }

    public Connection getConnection() {
        return this.dbconn;
    }
    
    public DbmResponse executeSQL(String query) throws SQLException {
        Statement stmt = this.dbconn.createStatement();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(query);
            System.out.println("Query executed");

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }

        int times = 0;
        String resparray = "";
        while (rs.next()) {
            times++;
            resparray += rs.getString(1);
            System.out.println(rs.getString(1));
        }
        System.out.println("times executed: " + times);
        if (times == 0) return new DbmResponse(DbmResponseType.ResponseEmpty);
        if (times == 1) return new DbmResponse(DbmResponseType.OneResponse, resparray);
        else return new DbmResponse(DbmResponseType.ResponseList, times, new String[0]);
    }

    /*
        For Production, the url will be jdbc:mysql://localhost:3306/FlyAway
        Username java, password is a hashed secret in an Environmental Variable
        Feel Free to use any other url or user/pass combo for dev
        TODO: NEVER PUT ANY RAW PASSWORDS IN AND COMMIT, IT IS MOSTLY IRREVERSIBLE
     */
    public void attemptConnection(String url, String user, String pass) {
        System.out.println("Attempting to connect to " + url);
        System.out.println(System.getenv("FLYAWAY_DBM_PWD"));
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Successfully connected to " + url);
            this.dbconn = connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to connect to " + url, e);
        }

    }
}
