package com.example.lc.controller;

import com.example.lc.service.GroqAPIService;
import com.example.lc.service.OllamaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ask")
public class OllamaController {

    private final OllamaService ollamaService;
    private final GroqAPIService groqAPIService;

    public OllamaController(OllamaService ollamaService, GroqAPIService groqAPIService) {
        this.ollamaService = ollamaService;
        this.groqAPIService = groqAPIService;
    }

    @GetMapping
    public String askOllama(@RequestParam String prompt) {
        return ollamaService.generateResponse(prompt);
    }

    @GetMapping("/groq")
    public String askGroq(@RequestParam String prompt) {
        return groqAPIService.getResponse(prompt).block();
    }
}

