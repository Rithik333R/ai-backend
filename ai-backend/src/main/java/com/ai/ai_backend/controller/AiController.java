package com.ai.ai_backend.controller;

import com.ai.ai_backend.model.CandidateProfile;
import com.ai.ai_backend.service.AiService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    // 🔴 UPDATED: Now properly catches the JSON Body sent by React
    @PostMapping
    public String chat(@RequestBody Map<String, String> payload) {
        // React might send the text as 'question' or 'message'. This checks both!
        String question = payload.containsKey("question") ? payload.get("question") : payload.get("message");

        // Defaults to chatId "1" if React forgets to send one
        String chatId = payload.getOrDefault("chatId", "1");

        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("The question/message cannot be empty.");
        }

        return aiService.askQuestion(chatId, question);
    }

    // Our extraction endpoint stays exactly the same
    @GetMapping("/extract")
    public CandidateProfile extractProfile(@RequestParam(defaultValue = "1") String chatId) {
        System.out.println("⚡ Starting AI PDF Extraction...");
        return aiService.extractCandidateProfile(chatId);
    }

    // 🔴 NEW API Endpoint for React to upload files
    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        if (file.isEmpty()) {
            return "ERROR: Please select a file to upload.";
        }
        return aiService.processUploadedResume(file);
    }
}