package com.ai.ai_backend.service;

import com.ai.ai_backend.memory.PersistentChatMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder chatClientBuilder, PersistentChatMemory chatMemory, VectorStore vectorStore) {

        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        // Advisor 1: Cures Amnesia (MySQL Chat History)
                        new MessageChatMemoryAdvisor(chatMemory)

                        // 🔴 TEMPORARILY DISABLED RAG ADVISOR
                        // new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults())
                )
                // Advisor 3: The Live Internet Tool
                .defaultFunctions("getLatestMatchScore")
                .build();
    }

    public String askQuestion(String chatId, String question) {
        return chatClient.prompt()
                .user(question)
                .advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .call()
                .content();
    }
}