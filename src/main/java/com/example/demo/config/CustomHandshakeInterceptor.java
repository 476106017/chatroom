package com.example.demo.config;

import com.example.demo.entity.Message;
import com.example.demo.repo.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@AllArgsConstructor
public class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    MessageRepository messageRepository;

    @Override
    public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request,
                                   org.springframework.http.server.ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            final String userName = authentication.getName();
            attributes.put("username", userName);

            Message message = new Message(userName, null, "已连接");
            messageRepository.save(message);
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(org.springframework.http.server.ServerHttpRequest request,
                               org.springframework.http.server.ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            final String userName = authentication.getName();
            Message message = new Message(userName, null, "已断开连接");
            messageRepository.save(message);
        }

        super.afterHandshake(request, response, wsHandler, ex);
    }
}
