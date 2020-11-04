package com.yj.gateway;


import com.yj.gateway.inbound.HttpInboundServer;

public class NettyServerApplication {
    
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    
    public static void main(String[] args) {
        String proxyServer1 = System.getProperty("proxyServer1","http://localhost:8801");
        String proxyServer2 = System.getProperty("proxyServer2","http://localhost:8802");
        String proxyServer3 = System.getProperty("proxyServer3","http://localhost:8803");

        String proxyPort = System.getProperty("proxyPort","8888");
        
          //  http://localhost:8888/api/hello  ==> gateway API
          //  http://localhost:8088/api/hello  ==> backend service
    
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        HttpInboundServer server = new HttpInboundServer(port, proxyServer1, proxyServer2, proxyServer3);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:["
                + proxyServer1 + "," + proxyServer2 + "," + proxyServer3 + "]");
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
