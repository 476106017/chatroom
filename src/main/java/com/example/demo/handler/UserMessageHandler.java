package com.example.demo.handler;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UserMessageHandler {

    final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public void sendMessage(String message, SimpMessageHeaderAccessor headerAccessor) {
        final String username = (String) headerAccessor.getSessionAttributes().get("username");
        // 发送消息到特定用户
        messagingTemplate.convertAndSendToUser(username, "/queue/msgConfirm", message);
        messagingTemplate.convertAndSend("/topic/public", message);
    }
}
