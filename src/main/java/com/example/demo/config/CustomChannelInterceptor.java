package com.example.demo.config;

import com.example.demo.entity.Message;
import com.example.demo.repo.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class CustomChannelInterceptor implements ChannelInterceptor {
    MessageRepository messageRepository;

    @Override
    public org.springframework.messaging.Message<?> preSend(org.springframework.messaging.Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null) {
            final Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

            // 尝试从simpUser中获取用户名
            String username = accessor.getUser() != null ? accessor.getUser().getName() : "Anonymous";

            // 如果simpUser为null，可以尝试从sessionAttributes中获取
            if (username.equals("Anonymous")) {
                username = (String) sessionAttributes.get("username");
            }

            String destination = accessor.getDestination();
            Object payload = message.getPayload();
            System.out.println("User: " + username + ", Destination: " + destination + ", Payload: " + payload);

            // 保存消息到数据库
            final Message messageDto = new Message(username, destination, new String((byte[]) payload));
            messageRepository.save(messageDto);

            // 将用户名、消息保存到线程存储
            sessionAttributes.put("msg", messageDto);
        }
        return message;
    }
}
