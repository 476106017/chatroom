package com.example.demo.handler;

import com.google.common.util.concurrent.Striped;
import com.example.demo.config.MyStorage;
import com.example.demo.repo.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.concurrent.locks.Lock;


@Controller
@AllArgsConstructor
public class UserMessageHandler {

    final SimpMessagingTemplate messagingTemplate;

    final MessageRepository messageRepository;


    @MessageMapping("/send")
    public void sendMessage(String message, SimpMessageHeaderAccessor accessor) {
        final MyStorage storage = MyStorage.of(accessor);
        messagingTemplate.convertAndSend("/topic/public", storage.getMsg());
    }

    private final Striped<Lock> readLockMap = Striped.lock(1024);

    @MessageMapping("/read")
    public void readMessage(Long messageId, SimpMessageHeaderAccessor accessor) {
        final Lock messageLock = readLockMap.get(messageId);
        final String user = MyStorage.of(accessor).getUser();

        messageLock.lock();
        try {
            messageRepository.findById(messageId).ifPresent(message -> {
                if(user.equals(message.getUsername())){
                    // 自己读不用管
                    return;
                }
                String readers = message.getReaders();
                if (readers == null || readers.isEmpty()) {
                    readers = user;
                } else {
                    if (!readers.contains(user)) {
                        readers = readers + "," + user;
                    }
                }
                message.setReaders(readers);
                messageRepository.save(message);
                messagingTemplate.convertAndSendToUser(message.getUsername(), "/queue/updateReaders", message);
            });
        }finally {
            messageLock.unlock();
        }

    }
}
