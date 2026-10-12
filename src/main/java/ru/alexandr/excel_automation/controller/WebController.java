package ru.alexandr.excel_automation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

// заглушки на которых проверяем безопасность до построения форм
@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("username", principal == null ? null : principal.getName());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
