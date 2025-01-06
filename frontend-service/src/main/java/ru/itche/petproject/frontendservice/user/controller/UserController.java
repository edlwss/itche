package ru.itche.petproject.frontendservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itche.petproject.frontendservice.user.client.ImplUserRestController;
import ru.itche.petproject.frontendservice.user.client.UserRestClient;
import ru.itche.petproject.frontendservice.user.entityRecord.UserToken;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRestClient userRestClient;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpSession session,
                                   Model model) {
        try {
            UserToken token = userRestClient.authenticate(username, password);
            session.setAttribute("token", token.getToken());
            session.setAttribute("role", token.getRole());
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "user/login";
        }
    }

}
