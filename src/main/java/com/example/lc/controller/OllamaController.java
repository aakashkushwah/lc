package com.example.lc.controller;

import com.example.lc.service.OllamaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ask")
public class OllamaController {

    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping
    public String askOllama(@RequestParam String prompt) {
        return ollamaService.generateResponse(prompt);
    }
}

