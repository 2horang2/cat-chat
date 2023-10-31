package com.horangya.catchat.chat.controller;

import com.horangya.catchat.chat.dto.ChatMessage;
import com.horangya.catchat.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
    private static final Set<String> SESSION_IDS = new HashSet<>();
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository messageRepository;

    @MessageMapping("/chat") // "/pub/chat"
    public void publishChat(ChatMessage chatMessage) {
        log.info("publishChat : {}", chatMessage);

        // 메시지를 저장
        chatMessage.setSentAt(LocalDateTime.now());
        messageRepository.save(chatMessage);

        messagingTemplate.convertAndSend("/sub/chat/" + chatMessage.getRoomId(), chatMessage);
    }

    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getChatMessages(@PathVariable Long roomId) {
        return messageRepository.findByRoomId(roomId);
    }

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        SESSION_IDS.add(sessionId);
        log.info("[connect] connections : {}", SESSION_IDS.size());
    }

    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        SESSION_IDS.remove(sessionId);
        log.info("[disconnect] connections : {}", SESSION_IDS.size());
    }
}