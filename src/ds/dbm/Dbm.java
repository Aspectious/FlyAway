package net.eastern.FlyAway.dbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbm {
    private Connection dbconn;
    public Dbm() {
        // For Testing
        attemptConnection("jdbc:mysql://localhost:3306/records", "java",System.getenv("FLYAWAY_DBM_PWD"));
    }

    /*
        For Production, the url will be jdbc:mysql://localhost:3306/records
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
