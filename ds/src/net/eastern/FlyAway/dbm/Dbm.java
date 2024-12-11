package net.eastern.FlyAway.dbm;

import net.eastern.FlyAway.util.Utils;

import java.sql.*;
import java.util.ArrayList;

public class Dbm {
    private Connection dbconn;

    /**
     * Constructor.
     */
    public Dbm() {
        String url = "jdbc:mysql://" + System.getenv("FLA_IP") + "/flyawaydev";
        String username = System.getenv("FLA_U");
        String pwd = System.getenv("FLA_P");
        dbconn = this.attemptConnection(url, username, pwd);
    }

    /**
     * Gets the Connection to the database using environmental variables
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + System.getenv("FLA_IP") + "/flyawaydev";
        String username = System.getenv("FLA_U");
        String pwd = System.getenv("FLA_P");
        return DriverManager.getConnection(url, username, pwd);
    }

    /**
     * The Great Big Handler that Handles all SQL Connections to the Database.
     * Makes life easier for us all, and is also a pain in the ass to mess with.
     * @param conn
     * @param qtype
     * @param sql
     * @return
     * @throws SQLException
     */
    public DbmResponse executeSQL(Connection conn, DbmQueryType qtype, String sql) throws SQLException {
        Statement stmt = conn.createStatement();

        if (qtype == DbmQueryType.QUERY) {
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery(sql);
                Utils.Debugprintln("[DBM] Query Executed");

            } catch (SQLException e) {
                System.err.println("[DBM] Error: " + e);
            }

            int times = 0;
            ArrayList<String> records = new ArrayList<String>();
            while (rs.next()) {
                times++;
                StringBuilder strresponse = new StringBuilder();

                ResultSetMetaData metadata = rs.getMetaData();
                metadata.getColumnCount();
                for (int i = 0; i < metadata.getColumnCount(); i++) {
                    strresponse.append(rs.getString(i + 1)).append(",");
                }
                String response = strresponse.toString().substring(0, strresponse.toString().length() - 1);
                records.add(response);
            }

            if (times == 0) return new DbmResponse(DbmResponseType.ResponseEmpty);
            if (times == 1) {
                String[] resparraylist = records.getFirst().split(",");
                return new DbmResponse(DbmResponseType.OneResponse, resparraylist);
            } else {
                return new DbmResponse(DbmResponseType.ResponseList, times, records);
            }
        } else {
            stmt.executeUpdate(sql);
            return new DbmResponse(DbmResponseType.ResponseEmpty);
        }
    }

    /**
     * Used to manually set the connecion.
     * Needed at times when a reconnect is needed.
     * @param conn
     */
    public void setConnection(Connection conn) {
        this.dbconn = conn;
    }

    /**
     * For Production, the url will be jdbc:mysql://localhost:3306/FlyAway
     * Username java, password is a hashed secret in an Environmental Variable
     * Feel Free to use any other url or user/pass combo for dev
     * TODO: NEVER PUT ANY RAW PASSWORDS IN AND COMMIT, IT IS MOSTLY IRREVERSIBLE
     * @param url
     * @param user
     * @param pass
     * @return
     */
    public Connection attemptConnection(String url, String user, String pass) {
        Utils.Debugprintln("Attempting to connect to " + url);
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            Utils.Debugprintln("Successfully connected to " + url);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to connect to " + url, e);
        }

    }
}
