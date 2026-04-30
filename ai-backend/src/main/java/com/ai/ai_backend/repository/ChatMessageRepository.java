package com.ai.ai_backend.repository;

import com.ai.ai_backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Spring Data JPA Magic: It reads the name of this method and writes the SQL for you!
    // Equivalent SQL: SELECT * FROM chat_history WHERE chat_id = ? ORDER BY timestamp ASC;
    List<ChatMessage> findByChatIdOrderByTimestampAsc(String chatId);

}