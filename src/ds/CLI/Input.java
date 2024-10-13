package net.eastern.FlyAway.CLI;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

import net.eastern.FlyAway.dbm.Dbm;
import net.eastern.FlyAway.dbm.DbmQueryType;
import net.eastern.FlyAway.dbm.DbmResponse;
import net.eastern.FlyAway.dbm.DbmResponseType;

public class Input {
    private Dbm dbm;

    public Input() throws SQLException {
        System.out.println("[info] CLI Command Handler Started");
        doInputQueryCycle();



        /*

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
        String[] args = new String[brokencmd.length - 1];
        System.arraycopy(brokencmd, 1, args, 0, brokencmd.length - 1);
        String command = brokencmd[0];
        ShUtils.Debugprintln("[Input] Running command: " + fullcommand);
        try {
            switch (command) {
                case "exit":
                    System.exit(0);
                    break;
                case "help":
                    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                    InputStream is = classloader.getResourceAsStream("cmds/help.txt");
                    Scanner scanfile = new Scanner(is);
                    while (scanfile.hasNextLine()) {
                        System.out.println(scanfile.nextLine());
                    }
                    scanfile.close();
                    break;
                case "exec":
                    dbm = new Dbm();
                    // For Testing
                    Connection conn23 = dbm.getConnection();
                    dbm.setConnection(conn23);
                    DbmResponse response2 = dbm.executeSQL(conn23, DbmQueryType.UPDATE, String.join(" ", args));
                    if (response2.getType() == DbmResponseType.OneResponse) {
                        System.out.println(response2.getContentArray()[0]);
                    } else if (response2.getType() == DbmResponseType.ResponseEmpty) {
                        System.out.println("No results found");
                    } else if (response2.getType() == DbmResponseType.ResponseList) {
                        for (int i = 0; i < response2.getContentArray().length; i++) {
                            System.out.println(response2.getContentArray()[i]);
                        }
                    }
                    break;
                case "setuserallow":
                    int exitallow = Integer.parseInt(args[1]);
                    System.out.println(String.join(" ", args));
                    boolean isint2 = false;
                    while (!isint2) {
                        try {
                            exitallow = Integer.parseInt(args[0]);
                            isint2 = true;
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid number");
                        }
                    }
                    try {
                        dbm = new Dbm();
                        Connection conn = dbm.getConnection();
                        DbmResponse updated = dbm.executeSQL(conn, DbmQueryType.UPDATE, "UPDATE users SET exitallowed = " + args[1] + " WHERE studentid = " + args[0]);
                        System.out.println("User updated");
                    } catch (SQLException e) {
                        System.err.println("Error: " + e);
                    }
                    break;
                case "validate":
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
                        Connection conn = dbm.getConnection();
                        dbm.setConnection(conn);
                        DbmResponse response = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT exitallowed FROM users WHERE studentid =" + sid);
                        ShUtils.Debugprintln("[Input] Response Type" + response.getType());
                        if (response.getType() == DbmResponseType.OneResponse) {
                            System.out.println(response.getContentArray()[0]);
                        } else if (response.getType() == DbmResponseType.ResponseEmpty) {
                            System.out.println(response.getContentArray()[0]);
                            String sql = "INSERT INTO users (studentid, exitallowed) VALUES (?, ?)";
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, sid);
                            pstmt.setBoolean(2, false);
                            pstmt.executeUpdate();
                            DbmResponse nullresponse = dbm.executeSQL(conn, DbmQueryType.QUERY, "SELECT studentid FROM users WHERE studentid = " + sid);
                            System.out.println("User added");

                        } else if (response.getType() == DbmResponseType.ResponseList) {
                            for (int i = 0; i < response.getContentArray().length; i++) {
                                System.out.println(response.getContentArray()[i]);
                            }
                            // We do a little Tomfoolery because I'mm too lazy to implement this yet
                            System.out.println("HOLY FUCKING SHIT OH FUCK I HAVENT BEEN CODED TO HANDLE THAT YET IM GONNA DIE");
                            throw new HolyFuckingShitIHaveNoClueWhatToDoBecauseIHaveNotBeenProgrammedToDoThatYetException("oopsie i returned multiple columns teehee :3");
                        }

                    } catch (HolyFuckingShitIHaveNoClueWhatToDoBecauseIHaveNotBeenProgrammedToDoThatYetException e) {
                        e.printStackTrace(System.err);
                        System.exit(-934782374);
                    }
                    //dbm.closeConnection();
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