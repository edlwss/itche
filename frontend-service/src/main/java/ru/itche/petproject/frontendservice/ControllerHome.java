package ru.itche.petproject.frontendservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerHome {
    @GetMapping("/")
    public String index() {
        return "index"; // Указывает на templates/index.html
    }
}
