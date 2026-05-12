package com.ai.ai_backend.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class InterviewSchedulerTool {

    // 1. The exact data the AI needs to gather before it can trigger this tool
    public record ScheduleRequest(String candidateName, String role, String date) {}

    // 2. The Tool Registration
    @Bean
    @Description("Schedules a technical or HR interview for a candidate. Use this whenever the user asks to schedule, book, or set up an interview.")
    public Function<ScheduleRequest, String> scheduleInterview() {
        return request -> {

            System.out.println("\n--- ⚡ AI TRIGGERED DATABASE ACTION ---");
            System.out.println("Booking Interview for: " + request.candidateName());
            System.out.println("Target Role: " + request.role());
            System.out.println("Date: " + request.date());
            System.out.println("---------------------------------------\n");

            // Note: In Phase 5, we will replace this return string with a MySQL save!
            return "SUCCESS: The interview for " + request.candidateName() +
                    " for the " + request.role() + " position on " + request.date() +
                    " has been officially booked in the system.";
        };
    }
}