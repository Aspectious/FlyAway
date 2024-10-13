package net.eastern.FlyAway.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {
    public Server(int portnum) throws Exception {
        System.out.println("[Setup] Starting FlyAway Server.....");
        HttpServer server = HttpServer.create(new InetSocketAddress(portnum),0);
        server.createContext("/", new RootHandler());
        server.createContext("/query", new QueryHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("[Setup] FlyAway Server started.");
    }
}
