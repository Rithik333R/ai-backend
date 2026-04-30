package com.ai.ai_backend.controller;

import com.ai.ai_backend.service.AiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithAi(
            @RequestParam(value = "chatId", defaultValue = "default-user") String chatId,
            @RequestBody Map<String, String> payload) {

        String question = payload.get("question");

        try {
            // 1. Try to get the answer from the Chef (Google's API)
            String aiResponse = aiService.askQuestion(chatId, question);
            return ResponseEntity.ok(aiResponse);

        } catch (Exception e) {
            // 2. If Google's servers crash, catch the error!
            System.err.println("Google API Error: " + e.getMessage());

            // 3. Send a polite 503 Service Unavailable response back to React
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Sorry, Google's AI servers are currently overloaded. Please wait 10 seconds and try again!");
        }
    }
}