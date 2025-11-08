package com.example.lc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QueryController {

    private static final Logger log = LoggerFactory.getLogger(QueryController.class);

    @GetMapping("/echo")
    public String echo(@RequestParam(name = "msg", required = false, defaultValue = "") String msg) {
        log.info("Received query parameter msg={}", msg);
        System.out.println("Received query parameter msg=" + msg);
        return "Received: " + msg;
    }
}

