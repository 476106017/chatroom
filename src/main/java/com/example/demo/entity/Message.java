package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "message")
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "status")
    private String status;

    @Column(name = "destination")
    private String destination;

    @Column(name = "content")
    private String content;

    @Column(name = "readers")
    private String readers;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public Message(String username, String destination, String content) {
        this.username = username;
        this.destination = destination;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}
