package net.eastern.FlyAway.CLI;
import java.sql.PreparedStatement;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.*;
import net.eastern.FlyAway.dbm.Dbm;
import net.eastern.FlyAway.dbm.DbmResponse;
import net.eastern.FlyAway.dbm.DbmResponseType;

public class Input {
    private Dbm dbm;
    public  Input() throws SQLException {
        System.out.println("[info] CLI Command Handler Started");
        doInputQueryCycle();



        /*
        Dbm dbm = new Dbm();

        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT exitallowed FROM users WHERE studentid = " + sid);
            System.out.println("Query executed");

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
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

        int times = 0;
        while (rs.next()) {
            times++;
            System.out.println(rs.getString(1));
        }
        System.out.println("times executed: " + times);
        */
    }

    public void doInputQueryCycle() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("# FLA-DS > ");
        processCommand(scanner.nextLine());
        doInputQueryCycle();
    }
    private void processCommand(String fullcommand) throws SQLException {
        String[] brokencmd = fullcommand.toLowerCase().split(" ");
        String[] args = new String[brokencmd.length-1];
        for (int i=1; i< brokencmd.length; i++) {
            args[i-1] = brokencmd[i];
        }
        String command = brokencmd[0];
        System.out.println(command);
        for (int i=0; i< args.length; i++) {
            System.out.println(args[i]);
        }
        if (Objects.equals(command, "exit")) System.exit(0);
        if (Objects.equals(command, "validate")) {
            int sid = 0;
            boolean isint = false;
            while (!isint) {
                try {
                    sid = Integer.parseInt(args[0]);
                    isint = true;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number");
                }
            }
            try {
                dbm = new Dbm();
                // For Testing
                DbmResponse response = dbm.executeSQL("SELECT exitallowed FROM users WHERE studentid =" + sid);
                System.out.println(response.getType());
                if (response.getType() == DbmResponseType.OneResponse) {
                    System.out.println(response.getContentArray()[0]);
                }
                else if (response.getType() == DbmResponseType.ResponseEmpty) {
                    System.out.println("Empty Response");
                }
                else if (response.getType() == DbmResponseType.ResponseList) {
                    // We do a little Tomfoolery because I'mm too lazy to implement this yet
                    System.out.println("HOLY FUCKING SHIT OH FUCK I HAVENT BEEN CODED TO HANDLE THAT YET IM GONNA DIE");
                    throw new HolyFuckingShitIHaveNoClueWhatToDoBecauseIHaveNotBeenProgrammedToDoThatYetException("oopsie i returned multiple columns teehee :3");
                }

            } catch (HolyFuckingShitIHaveNoClueWhatToDoBecauseIHaveNotBeenProgrammedToDoThatYetException e) {
                e.printStackTrace(System.err);
                System.exit(-934782374);
            }
            dbm.closeConnection();
        }
    }

}