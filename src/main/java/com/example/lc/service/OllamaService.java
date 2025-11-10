package com.example.lc.service;

import com.example.lc.util.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class OllamaService {

    private final WebClient webClient;

    public OllamaService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:11434").build();
    }

    public String generateResponse(String prompt) {
        Map<String, Object> request = Map.of(
                "model", "llama3",  // change to your model name
                "prompt", prompt
        );

        // Ollama streams results; we’ll collect into a string
        String res =  webClient.post()
                .uri("/api/generate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Utils.getResponseFromOllamaResponse(res);
    }
}

