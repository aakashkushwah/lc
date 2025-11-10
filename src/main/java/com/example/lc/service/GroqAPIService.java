package com.example.lc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class GroqAPIService {

    private final WebClient webClient;
    private final String model;

    public GroqAPIService(
            WebClient.Builder builder,
            @Value("${groq.base-url}") String baseUrl,
            @Value("${groq.api-key}") String apiKey,
            @Value("${groq.model}") String model
    ) {
        this.webClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
        this.model = model;
    }

    public Mono<String> getResponse(String userPrompt) {
        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", userPrompt)
                )
        );

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(responseMap -> {
                    List<Map<String, Object>> choices =
                            (List<Map<String, Object>>) responseMap.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> first = choices.get(0);
                        Map<String, String> message =
                                (Map<String, String>) first.get("message");
                        return message.get("content");
                    }
                    return "No response";
                });
    }
}

