package net.eastern.FlyAway.api;

import net.eastern.FlyAway.util.Utils;
import net.eastern.FlyAway.web.Server;

public class APIServer extends Server {

    public APIServer(int portnum) throws Exception {
        super(portnum);
        Utils.Infoprintln("Setup API Server using HTTPS Server.");
        super.createContext("/",new APIHandler());
        super.start();
    }
}
