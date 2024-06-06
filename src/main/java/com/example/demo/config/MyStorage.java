package com.example.demo.config;

import com.example.demo.entity.Message;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.Map;

@Data
public class MyStorage {
    private String user;
    private Message msg;

    public static MyStorage of(SimpMessageHeaderAccessor accessor){
        MyStorage _return = new MyStorage();
        final Map<String, Object> attr = accessor.getSessionAttributes();
        _return.user = (String) attr.get("username");
        _return.msg = (Message) attr.get("msg");
        return _return;
    }

}
