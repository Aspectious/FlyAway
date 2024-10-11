package net.eastern.FlyAway.CLI;
import java.sql.PreparedStatement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.*;
import net.eastern.FlyAway.dbm.Dbm;

public class Input {
    public  Input() throws SQLException {
        System.out.println("[input] Started");
        String url = "jdbc:mysql://75.18.104.248:3306/flyawaydev";
        String username = "aspectious";
        String password = "p@ssw0rd123";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID: ");
        int sid = 0;
        boolean isint = false;
        while (!isint) {
            try {
                sid = scanner.nextInt();
                isint = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.next();
            }
        }



        Dbm dbm = new Dbm();
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT exitallowed FROM users WHERE studentid = " + sid);
            System.out.println("Query executed");

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        /*
        if(!rs.next()) {
            String sql = "INSERT INTO users (studentid, exitallowed) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sid);
            pstmt.setBoolean(2, false);
            pstmt.executeUpdate();
            rs = stmt.executeQuery("SELECT studentid FROM users WHERE studentid = " + sid);
            System.out.println("User added");


        }
        if (rs == null) {
            System.out.println("No results found");
        }
        */
        int times = 0;
        while (rs.next()) {
            times++;
            System.out.println(rs.getString(1));
        }
        System.out.println("times executed: " + times);


    }

}