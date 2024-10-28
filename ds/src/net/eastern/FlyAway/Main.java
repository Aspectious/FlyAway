package net.eastern.FlyAway;

import net.eastern.FlyAway.cli.Input;
import net.eastern.FlyAway.util.Utils;
import net.eastern.FlyAway.web.Server;

/**
 * Flyaway Dedicated Server
 * @author aspectious
 * @author sushibirb
 * @version 1.14-A
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Utils.loadMap();
        Server webserver = new Server(8000);
        Input input = new Input();
        //Dbm databasemanager = new Dbm();
    }
}
