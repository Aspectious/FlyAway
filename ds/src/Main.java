package net.eastern.FlyAway;

import net.eastern.FlyAway.CLI.Input;
import net.eastern.FlyAway.web.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        Util.loadMap();
        Server webserver = new Server(443);
        Input input = new Input();
        //Dbm databasemanager = new Dbm();
    }
}
