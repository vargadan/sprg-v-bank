package com.dani.vbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(Model model, @RequestParam(value="input", required=false, defaultValue="world") String name) {
        model.addAttribute("output", name);
        return "hello";
    }
}