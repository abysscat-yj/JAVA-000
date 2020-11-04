# 学习笔记

---

### 1.（必做）整合你上次作业的 httpclient
将上一次的作业完成的netty集成进来(xxx.xxx.backserver)，作为本地后台服务启动(BackServerApplication)，绑定端口8088
启动本次示例的网关服务(xxx.xxx.gateway.NettyServerApplication)，设置代理服务地址为启动的本地后台地址+端口（http://localhost:8088/api/hello），代理端口设置为8888
启动后，通过浏览器访问http://localhost:8888/api/hello，本地网关服务收到请求后，会访问后台服务地址，即http://localhost:8088/api/hello，由此实现简易的网关操作

### 2.（选做）使用 netty 实现后端 http 访问（代替上一步骤）
自定义client.netty4.NettyHttpClient类，用于代替OkHttpClient实现Http请求
自定义HttpClientInboundHandler类，用于监听NettyHttpClient的请求回调

### 3.（必做）实现过滤器
HttpInboundHandler类实现HttpRequestFilter接口，自定义过滤器操作
OkhttpOutboundHandler类中，调动网络请求前，复制客户端请求的headers


### 4.（选做）实现路由
HttpInboundHandler类实现HttpEndpointRouter接口，自定义路由（随机路由）
