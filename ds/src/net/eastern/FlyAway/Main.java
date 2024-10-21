package net.eastern.FlyAway;

import net.eastern.FlyAway.cli.Input;
import net.eastern.FlyAway.util.Utils;
import net.eastern.FlyAway.web.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        Utils.loadMap();
        Server webserver = new Server(8000);
        Input input = new Input();
        //Dbm databasemanager = new Dbm();
    }
}
