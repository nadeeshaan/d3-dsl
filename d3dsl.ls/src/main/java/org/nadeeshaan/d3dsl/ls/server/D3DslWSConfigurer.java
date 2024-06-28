package org.nadeeshaan.d3dsl.ls.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class D3DslWSConfigurer implements WebSocketConfigurer {

    private final WebSocketHandler websocketHandler;

    public D3DslWSConfigurer(WebSocketHandler websocketHandler) {
        this.websocketHandler = websocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketHandler, "/").setAllowedOrigins("*");
    }
}
