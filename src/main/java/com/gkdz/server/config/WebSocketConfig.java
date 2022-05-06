package com.gkdz.server.config;

import com.gkdz.server.common.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

@Slf4j
@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    /**
     * 修改握手,就是在握手协议建立之前修改其中携带的内容
     *
     * @param sec
     * @param request
     * @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        /**
         * 项目中使用Shiro，所以一开始就进行了登录拦截。执行到这里说明已经登录验证通过（在url后面加上token参数）
         * 将用户信息存入
         */
        sec.getUserProperties().put("user", ShiroUtils.getUserEntity());
        super.modifyHandshake(sec, request, response);


    }
}
