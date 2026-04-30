package com.ai.ai_backend.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class SportsTool {

    public record SportsRequest(String teamName) {}

    @Bean
    @Description("Get the latest match results and scores for any Indian Premier League (IPL) sports team. Use this whenever a user asks about a recent game, score, or if a team won.")
    public Function<SportsRequest, String> getLatestMatchScore() {
        return request -> {
            System.out.println("⚡ AI TRIGGERED TOOL: Fetching live data for team: " + request.teamName());

            // Simulating a successful JSON response from an external sports API
            String team = request.teamName().toUpperCase();

            if (team.contains("MI") || team.contains("MUMBAI")) {
                return "{ \"team\": \"Mumbai Indians\", \"opponent\": \"KKR\", \"result\": \"Won\", \"score\": \"MI 185/5, KKR 184/8\", \"highlights\": \"Akash Madhwal took 3 crucial wickets.\" }";
            } else if (team.contains("RCB") || team.contains("ROYAL CHALLENGERS")) {
                return "{ \"team\": \"Royal Challengers\", \"opponent\": \"Capitals\", \"result\": \"Lost\", \"score\": \"RCB 170/8, DC 171/4\" }";
            } else if (team.contains("CSK")) {
                return "{ \"team\": \"CSK\", \"opponent\": \"SRH\", \"result\": \"Won\", \"score\": \"CSK 210/3, SRH 190/8\" }";
            }

            return "{ \"error\": \"Live data not available for this team at the moment.\" }";
        };
    }
}