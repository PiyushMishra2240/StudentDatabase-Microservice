package com.studentVersion2.Studentversion2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactController {
    @GetMapping("/")
    public String index() {
        return "index.html"; // "index" here refers to the name of your React HTML file (e.g., index.html)
    }
}
