package com.example.demo.repo;

import com.example.demo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.timestamp >= :startDate and destination='/app/send' ORDER BY m.timestamp DESC")
    List<Message> findRecentMessages(@Param("startDate") LocalDateTime startDate);
}