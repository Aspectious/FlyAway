package net.eastern.FlyAway.api;

import net.eastern.FlyAway.util.Utils;
import net.eastern.FlyAway.web.Server;

public class APIServer extends Server {

    /**
     * API Server, child class of the HTTPS Server
     * @param portnum
     * @throws Exception
     */
    public APIServer(int portnum) throws Exception {
        super(portnum);
        Utils.Infoprintln("Setup API Server using HTTPS Server.");
        super.createContext("/",new APIHandler());
        super.start();
    }
}
