package com.metrics.api.controller;
import org.springframework.web.bind.annotation.*;

@RestController
class MainController {
    @GetMapping("/")
    public String start() {
            return "Service started...";
    }
        @GetMapping("/ping")
        public String ping() {
                return "pon";
        }
}