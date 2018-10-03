package com.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("message", "Welcome Chat Room");
        return "index";
    }
}