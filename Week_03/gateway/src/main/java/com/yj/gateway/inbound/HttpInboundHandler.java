package com.yj.gateway.inbound;

import com.yj.gateway.filter.HttpRequestFilter;
import com.yj.gateway.outbound.httpclient.HttpOutboundHandler;
import com.yj.gateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter implements HttpRequestFilter, HttpEndpointRouter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String[] proxyServer;
    private HttpOutboundHandler handler;
    
    public HttpInboundHandler(String[] proxyServer) {
        this.proxyServer = proxyServer;
        String realProxyServer = route(Arrays.asList(proxyServer));
        handler = new HttpOutboundHandler(realProxyServer);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {

            FullHttpRequest fullRequest = (FullHttpRequest) msg;

            String uri = fullRequest.uri();

            System.out.println("channelRead流量接口请求开始，uri：" + uri + "，时间为：" + LocalDateTime.now().toString());

            if (uri.contains("favicon.ico")) {
                return;
            }

            handler.handle(fullRequest, ctx);
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        // 加入自定义header
        HttpHeaders headers = fullRequest.headers();
        headers.add("nio", "yj");
    }

    @Override
    public String route(List<String> endpoints) {
        // 随机路由
        int randomInt = new Random().nextInt(1000);
        System.out.println("随机数：" + randomInt);
        return endpoints.get(randomInt % endpoints.size());
    }
}
