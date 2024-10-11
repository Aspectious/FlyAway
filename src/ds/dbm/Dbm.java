package net.eastern.FlyAway.dbm;

import net.eastern.FlyAway.CLI.ShUtils;

import javax.xml.transform.Result;
import java.sql.*;

public class Dbm {
    private Connection dbconn;
    public Dbm() {
        String url = "jdbc:mysql://" + System.getenv("FLA_IP") + "/flyawaydev";
        String username = System.getenv("FLA_U");
        String pwd = System.getenv("FLA_P");
        dbconn = this.attemptConnection(url,username,pwd);
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + System.getenv("FLA_IP") + "/flyawaydev";
        String username = System.getenv("FLA_U");
        String pwd = System.getenv("FLA_P");
        return DriverManager.getConnection(url, username, pwd);
    }

    public DbmResponse executeSQL(Connection conn, DbmQueryType qtype, String sql) throws SQLException {
        Statement stmt = conn.createStatement();

        if (qtype == DbmQueryType.QUERY) {
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery(sql);
                ShUtils.Debugprintln("[DBM] Query Executed");

            } catch (SQLException e) {
                System.err.println("[DBM] Error: " + e);
            }

            int times = 0;
            StringBuilder resparray = new StringBuilder();
            while (rs.next()) {
                times++;
                StringBuilder strresponse = new StringBuilder();

                ResultSetMetaData metadata = rs.getMetaData();
                metadata.getColumnCount();
                for (int i=0; i<metadata.getColumnCount(); i++) {
                    strresponse.append(rs.getString(i + 1)).append(",");
                }
                strresponse.substring(0, strresponse.length()-1);
                resparray.append(strresponse).append(",");
            }
            if (!resparray.isEmpty()) resparray.substring(0, resparray.length()-1);
            String[] resparraylist = resparray.toString().split(",");
            if (times == 0) return new DbmResponse(DbmResponseType.ResponseEmpty);
            if (times == 1) return new DbmResponse(DbmResponseType.OneResponse, resparraylist[0]);
            return new DbmResponse(DbmResponseType.ResponseList, times, resparraylist);
        } else {
            stmt.executeUpdate(sql);
            return new DbmResponse(DbmResponseType.ResponseEmpty);
        }

    }

    public void setConnection(Connection conn) {
        this.dbconn = conn;
    }
    public void closeConnection() {
        /*
        try {
            System.out.println("Closing Connection");
            //dbconn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */
    }

    /*
        For Production, the url will be jdbc:mysql://localhost:3306/FlyAway
        Username java, password is a hashed secret in an Environmental Variable
        Feel Free to use any other url or user/pass combo for dev
        TODO: NEVER PUT ANY RAW PASSWORDS IN AND COMMIT, IT IS MOSTLY IRREVERSIBLE
     */
    public Connection attemptConnection(String url, String user, String pass) {
        ShUtils.Debugprintln("[DBM] Attempting to connect to " + url);
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            ShUtils.Debugprintln("[DBM] Successfully connected to " + url);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("[DBM] Unable to connect to " + url, e);
        }

    }
}
