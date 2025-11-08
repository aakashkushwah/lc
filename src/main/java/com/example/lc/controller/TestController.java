package com.example.lc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller with a test endpoint that echoes and prints the query parameter passed.
 * Usage: GET /test?q=hello
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * Test endpoint that logs and returns the provided query parameter `q`.
     * @param q optional query parameter
     * @return a simple acknowledgment string containing the value of q
     */
    @GetMapping("/test")
    public String test(@RequestParam(value = "q", required = false) String q) {
        // Log via slf4j logger
        logger.info("Received query parameter q={}", q);
        // Also print to stdout as requested
        System.out.println("Received query parameter q=" + q);

        return "Received q=" + (q == null ? "" : q);
    }
}

