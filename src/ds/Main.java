package net.eastern.FlyAway;



import net.eastern.FlyAway.dbm.Dbm;
import net.eastern.FlyAway.web.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        Server webserver = new Server(8000);
        //Dbm databasemanager = new Dbm();
    }
}
