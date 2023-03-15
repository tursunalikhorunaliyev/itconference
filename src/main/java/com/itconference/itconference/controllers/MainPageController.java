package com.itconference.itconference.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping("/it-conference-kuva")
    public String home(){
        return "index";
    }
}
