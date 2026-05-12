package com.ai.ai_backend.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {

        System.out.println("--- 🤖 INITIALIZING EMPTY VECTOR DATABASE ---");

        // We no longer hardcode a PDF here. It starts completely empty!
        // It will only learn when you upload a file via the React UI.
        return new SimpleVectorStore(embeddingModel);
    }
}