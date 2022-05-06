package com.gkdz.server.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Slf4j
//@Configuration
//@EnableWebSocketMessageBroker
public class StompWebsocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册STOMP协议的端点(endpoint)，主要是起到连接作用
     * 也就是客户端和服务端建立websocket连接的地址，例如：
     * var socket = new SockJS("http://localhost:9000/stomp/websocket");
     * var stompClient = Stomp.over(socket);
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一个连接端点，客户端和服务端建立websocket连接的地址（一般叫endpoint端点）
        registry.addEndpoint("/stomp/websocket")
                // .setHandshakeHandler() 可以设置自定义握手处理，主要是在客服端和服务端建立连接的时候认证校验用户身份
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

                        //                        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
                        //                        String token = req.getServletRequest().getParameter("token");

                        String token = (String) attributes.get("token");
                        log.info("determineUser---->token={}", token);


                        return () -> token;
                    }
                })
                //可以添加自定义拦截器
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
                        ServletServerHttpRequest req = (ServletServerHttpRequest) serverHttpRequest;
                        String token = req.getServletRequest().getParameter("token");

                        if (StrUtil.isEmpty(token)) {
                            return false;
                        }
                        attributes.put("token", token);
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

                    }
                })
                // 允许跨域访问
                .setAllowedOrigins("*")
                // 需要使用SockJS协议建立连接
                .withSockJS();
    }

    /**
     * 配置消息代理(Message Broker)
     * 在新建连接之后就可以订阅消息代理，例如：
     * stompClient.connect(headers, function (frame) {
     * stompClient.subscribe("/topic/message", function (response) {
     * // response.body消息内容，作出相应处理
     * });
     * });
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 这里使用的是基于内存的方式，正式环境可以配置MQ中间件，例如：rabbitmq、activemq等
        // 用于客户端订阅监听的地址前缀，这里可以添加多个前缀，一般将队列（点对点、一对一消息）和广播（群发、一对多消息）区分开即可
        // 点对点用/queue消息代理，广播用/topic消息代理
        registry.enableSimpleBroker("/common", "/topic", "/user");
        // 客户端给服务端发送消息的统一前缀，如果配置了这个，在客户端发送的时候一定要加前缀/app，否则服务端无法接收到客户端请求消息，例如：
        // stompClient.send("/app/broadcast/string", {}, $("#broadcast_message").val());
        // 注意：
        // 若使用了setApplicationDestinationPrefixes方法，则作用主要体现在@SubscribeMapping和@MessageMapping上。
        // 如控制层配置@MessageMapping("/sendToServer")，则客户端发送的地址是 /app/sendToServer
        registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的订阅前缀，不设置的话，默认也是/user/
        // 注意：如果registry.setUserDestinationPrefix("/user")不使用默认配置的前缀/user，则需要
        // 把/user加在registry.enableSimpleBroker，否则客户端订阅用/user前缀是接收不到消息的！！！！
        // 真是坑啊！！！！害我调了一整天，折腾浪费了我一整天时间找bug！！！
        // stompClient.subscribe("/user/" + userId + "/notice", function (response) {});
        // registry.enableSimpleBroker("/topic", "/queue", "/user");只有加了"/user"，上面这个订阅才能生效！！！
        // registry.setUserDestinationPrefix("/user");
        // 这里我就不用默认的/user了，使用/queue最为点对点前缀即可，这样客户端就是使用queue前缀订阅点对点消息，例如：
        // stompClient.subscribe("/queue/" + userId + "/notice", function (response) {});
        registry.setUserDestinationPrefix("/toUser");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                //                StompHeaderAccessor.wrap(message);
                return message;

            }
        });

    }
}
