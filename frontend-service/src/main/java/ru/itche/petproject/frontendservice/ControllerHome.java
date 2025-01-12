package ru.itche.petproject.frontendservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerHome {
    @GetMapping("/")
    public String login() {
        return "user/login";
    }

    @GetMapping("/musical-school")
    public String index() {
        return "home";
    }
}
