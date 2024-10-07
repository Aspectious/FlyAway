package net.eastern.FlyAway

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import cpm.sun.net.httpserver.*;
Public class  {
  public static void main(String[]args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
    server.createContext("/", new MyHandler());
    server.setExecutor(null);
    server.start();
  }

  static class MyHandler implements HttpHandler {
    @override
    public void handle(HttpExchange t) throws IOException {
      String response = "This is the response";
      t.sendResponseHeaders(200,response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }
}
