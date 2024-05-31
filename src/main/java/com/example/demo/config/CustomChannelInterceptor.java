package com.example.demo.config;

import com.example.demo.entity.UserHistory;
import com.example.demo.repo.UserHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomChannelInterceptor implements ChannelInterceptor {
    UserHistoryRepository userHistoryRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null) {
            // 尝试从simpUser中获取用户名
            String username = accessor.getUser() != null ? accessor.getUser().getName() : "Anonymous";

            // 如果simpUser为null，可以尝试从sessionAttributes中获取
            if (username.equals("Anonymous")) {
                username = (String) accessor.getSessionAttributes().get("username");
            }

            String destination = accessor.getDestination();
            Object payload = message.getPayload();
            System.out.println("User: " + username + ", Destination: " + destination + ", Payload: " + payload);

            // 保存消息到数据库
            final UserHistory history = new UserHistory(username, destination, new String((byte[]) payload));
            userHistoryRepository.save(history);
        }
        return message;
    }
}
