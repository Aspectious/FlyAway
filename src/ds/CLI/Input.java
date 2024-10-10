package net.eastern.FlyAway.CLI;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import net.eastern.FlyAway.dbm.Dbm;

public class Input {
    public Input() {
        Scanner test = new Scanner(System.in);
        int sid = test.nextInt();
        Dbm dbc = new Dbm();
        Connection conn = dbc.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String sstmt = null;
        try {
            sstmt = String.valueOf(sid);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT studentid FROM users");
            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...
            try {
                if (stmt.execute("SELECT studentid FROM users")) {
                    rs = stmt.getResultSet();
                    System.out.println(rs.getInt(0));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Now do something with the ResultSet ....
        } catch (
                SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }


        }
    }
}
