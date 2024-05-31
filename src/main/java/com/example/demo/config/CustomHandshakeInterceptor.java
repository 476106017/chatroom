package com.example.demo.config;

import com.example.demo.entity.UserHistory;
import com.example.demo.repo.UserHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.WebSocketHttpRequestHandler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
public class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    UserHistoryRepository userHistoryRepository;

    @Override
    public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request,
                                   org.springframework.http.server.ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            final String userName = authentication.getName();
            attributes.put("username", userName);

            UserHistory userHistory = new UserHistory(userName, null, "已连接");
            userHistoryRepository.save(userHistory);
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
            UserHistory userHistory = new UserHistory(userName, null, "已断开连接");
            userHistoryRepository.save(userHistory);
        }

        super.afterHandshake(request, response, wsHandler, ex);
    }
}
