package com.example.demo.handler;

import com.example.demo.entity.Message;
import com.google.common.util.concurrent.Striped;
import com.example.demo.config.MyStorage;
import com.example.demo.repo.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;


@Controller
@AllArgsConstructor
public class UserMessageHandler {

    final SimpMessagingTemplate messagingTemplate;

    final MessageRepository messageRepository;


    @MessageMapping("/init")
    public void initMessage(SimpMessageHeaderAccessor accessor) {
        final MyStorage storage = MyStorage.of(accessor);

        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        List<Message> recentMessages = messageRepository.findRecentMessages(threeDaysAgo);
        final List<Message> messages = recentMessages.stream().limit(20).toList().reversed();

        messagingTemplate.convertAndSendToUser(storage.getUser(),"/topic/init", messages);
    }

    @MessageMapping("/send")
    public void sendMessage(String message, SimpMessageHeaderAccessor accessor) {
        final MyStorage storage = MyStorage.of(accessor);

        messagingTemplate.convertAndSend("/topic/public", storage.getMsg());
    }

    private final Striped<Lock> readLockMap = Striped.lock(1024);
    @MessageMapping("/read")
    public void readMessage(String msg, SimpMessageHeaderAccessor accessor) {
        final String user = MyStorage.of(accessor).getUser();
        final List<Long> messageIds = Arrays.stream(msg.split(",")).map(Long::valueOf).toList();
        messageIds.forEach(messageId->{
            final Lock messageLock = readLockMap.get(messageId);
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
        });
    }

    @MessageMapping("/withdraw")
    public void withdrawMessage(Long messageId, SimpMessageHeaderAccessor accessor) {
        final String user = MyStorage.of(accessor).getUser();

        messageRepository.findById(messageId).ifPresent(message -> {
           if(message.getUsername().equals(user)){
               message.setStatus("WITHDRAW");
               messageRepository.save(message);
               messagingTemplate.convertAndSend("/queue/withdraw", messageId);
           }
        });
    }
}
