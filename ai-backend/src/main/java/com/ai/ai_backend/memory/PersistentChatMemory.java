package com.ai.ai_backend.memory;

import com.ai.ai_backend.entity.ChatMessage;
import com.ai.ai_backend.repository.ChatMessageRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersistentChatMemory implements ChatMemory {

    private final ChatMessageRepository repository;

    // We inject your database elevator here!
    public PersistentChatMemory(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        for (Message msg : messages) {
            // 1. Check who is speaking (The User or the AI?)
            String role = (msg instanceof UserMessage) ? "user" : "assistant";

            // 2. Translate Spring's Message into our Database Blueprint
            ChatMessage entity = new ChatMessage(conversationId, role, msg.getContent());

            // 3. Save it to MySQL
            repository.save(entity);
        }
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        // 1. Pull the history out of MySQL
        List<ChatMessage> entities = repository.findByChatIdOrderByTimestampAsc(conversationId);

        // 2. Translate the MySQL data back into a format Spring AI understands
        List<Message> springAiMessages = new ArrayList<>();
        for (ChatMessage entity : entities) {
            if ("user".equals(entity.getRole())) {
                springAiMessages.add(new UserMessage(entity.getContent()));
            } else {
                springAiMessages.add(new AssistantMessage(entity.getContent()));
            }
        }

        // 3. Only return the last N messages so we don't overwhelm the AI's token limit
        int startIndex = Math.max(0, springAiMessages.size() - lastN);
        return springAiMessages.subList(startIndex, springAiMessages.size());
    }

    @Override
    public void clear(String conversationId) {
        // We will leave this blank for now. You could add a "delete history" feature later!
    }
}