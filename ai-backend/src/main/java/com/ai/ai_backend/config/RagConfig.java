package com.ai.ai_backend.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader; // 🔴 NEW IMPORT
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class RagConfig {

    // 1. Point this to the new PDF file!
    @Value("classpath:my_resume.pdf")
    private Resource pdfFile;

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {

        SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingModel);

        System.out.println("--- 🤖 RAG SYSTEM BOOTING UP ---");

        // 2. Use the PDF Reader instead of the Text Reader!
        System.out.println("1. Extracting text from PDF Document...");
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfFile);
        List<Document> documents = pdfReader.get();

        System.out.println("2. Slicing PDF text into arrays (Chunking)...");
        TokenTextSplitter textSplitter = new TokenTextSplitter();
        List<Document> splitDocuments = textSplitter.apply(documents);

        System.out.println("3. Translating text to Math Vectors (Local ONNX Model)...");
        vectorStore.add(splitDocuments);

        System.out.println("--- ✅ PDF RAG SYSTEM READY ---");

        return vectorStore;
    }
}