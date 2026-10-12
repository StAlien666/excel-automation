package ru.alexandr.excel_automation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

// Проверочная точка для ролей, двойная защита пускай будет
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/ping")
    public String ping(Principal principal) {
        return "роли работают, " + principal.getName();
    }
}