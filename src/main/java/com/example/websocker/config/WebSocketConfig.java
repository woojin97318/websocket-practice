package com.example.websocker.config;

import com.example.websocker.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocketConfigurer는 WebSocket을 활성화하고, WebSocketHandler를 등록할 수 있게 해준다.
 * registerWebSocketHandlers() 메서드를 통해 WebSocketHandler를 등록할 수 있다.
 * 또한, registerWebSocketHandlers에 연결할 WebSocket 엔드 포인트("ws/chat")을 등록할 수 있다.
 * setAllowedOrigin은 지정한 Origin에서 오는 요청만 허용한다.
 */

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
    }
}
