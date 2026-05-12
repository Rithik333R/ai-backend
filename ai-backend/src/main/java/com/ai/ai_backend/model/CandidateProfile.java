package com.ai.ai_backend.model;

import java.util.List;

// A modern Java Record that defines exactly what we want the AI to extract
public record CandidateProfile(
        String fullName,
        String location,
        List<String> topSkills,
        String currentGoal,
        int estimatedYearsOfExperience
) {}