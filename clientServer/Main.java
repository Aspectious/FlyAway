package net.eastern.FlyAway;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;
public class Main {
  public static void main(String[]args) throws Exception {
    System.out.println("Starting FlyAway Server.....");
    HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
    server.createContext("/", new MyHandler());
    server.setExecutor(null);
    server.start();
    System.out.println("FlyAway Server started");
  }

  static class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

      // Print Debug info for request
      System.out.println("[Info] New Request, origin \"" + t.getRemoteAddress().getAddress() + ":" + t.getRemoteAddress().getPort() + "\", target \"" + t.getRequestURI() + "\"");

      String response = "200 OK";
      t.sendResponseHeaders(200,response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }
}
