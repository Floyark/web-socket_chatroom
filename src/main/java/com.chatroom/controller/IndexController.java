package com.chatroom.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Value("${ws.server}")
    private String wsServerUrl;

    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("message", "Welcome Chat Room");
        model.addAttribute("ws_server", wsServerUrl);
        return "index";
    }
}