package com.ai.ai_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_history")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId; // To remember WHICH conversation this belongs to

    private String role; // To remember WHO said it ("user" or "assistant")

    @Column(columnDefinition = "TEXT")
    private String content; // The actual message text

    private LocalDateTime timestamp; // When the message was sent

    // --- Constructors ---
    public ChatMessage() {
        // JPA requires a default empty constructor!
    }

    public ChatMessage(String chatId, String role, String content) {
        this.chatId = chatId;
        this.role = role;
        this.content = content;
        this.timestamp = LocalDateTime.now(); // Automatically set the time
    }

    // --- Getters (Required for Spring to read the data) ---
    public Long getId() { return id; }
    public String getChatId() { return chatId; }
    public String getRole() { return role; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
}