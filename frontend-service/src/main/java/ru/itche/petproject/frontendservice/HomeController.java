package ru.itche.petproject.frontendservice;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HttpSession session;

    @GetMapping("/")
    public String login() {
        return "user/login";
    }

    @GetMapping("/musical-school")
    public String index(Model model) {
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("role", session.getAttribute("role"));
        return "home";
    }
}
