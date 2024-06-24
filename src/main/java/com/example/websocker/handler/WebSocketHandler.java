package com.example.websocker.handler;

import com.example.websocker.service.ChatService;
import com.example.websocker.vo.ChatMessage;
import com.example.websocker.vo.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * WebSocket 연결에 대한 로직처리 담당.
 * 클라이언트나 서버에서 메세지를 보내면, WebSocket에서 처리 후 다시 메세지를 보내는 역할 담당
 * 대표 메서드
 *      - afterConnectionEstablished(WebSocketSession session) : WebSocket 연결이 맺어진 이후 이를 처리하는 메서드
 *      - handleMessage(WebSocketSession session, WebSocketMessage<?> message) : 메세지를 받았을때 이를 처리하기 위해 사용
 * WebSession : WebSocket 연결에 대한 세션 정보 제공
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper; // payload를 ChatMessage 객체로 만들어 주기 위한 objectMapper
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 메세지 가져옴
        String payload = message.getPayload();

        // payload를 ChatMessage 객체로 생성
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        // ChatMessage 객체에서 roomId를 가져와 일치하는 room 주입
        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());

        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}
