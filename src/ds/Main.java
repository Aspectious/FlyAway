package net.eastern.FlyAway;

import net.eastern.FlyAway.CLI.Input;
import net.eastern.FlyAway.web.Server;

public class Main {
    public static void main(String[] args) throws Exception {

        Server webserver = new Server(8000);
        Input input = new Input();
        //Dbm databasemanager = new Dbm();
    }
}
