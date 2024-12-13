package net.eastern.FlyAway;

import net.eastern.FlyAway.api.APIServer;
import net.eastern.FlyAway.cli.Input;
import net.eastern.FlyAway.util.Utils;

/**
 * Flyaway Dedicated Server
 * @author aspectious
 * @author sushibirb
 * @version 1.14-A
 */
public class Main {
    public static void main(String[] args) throws Exception {
        APIServer apiserver = new APIServer(8000);
        Input input = new Input();
    }
}
