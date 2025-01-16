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
            session.setAttribute("userId", token.getUserId());


            if (token.getRole().equals("ROLE_TEACHER")) {
                return "redirect:/musical-school/teachers/teacher/" + token.getUserId();
            } else if (token.getRole().equals("ROLE_STUDENT")) {
                return "redirect:/musical-school/students/student/" + token.getUserId();
            } else if (token.getRole().equals("ROLE_ADMIN")) {
                return "redirect:/musical-school/admin/" + token.getUserId();
            }

            return "redirect:/musical-school"; // На случай, если роль неизвестна
        } catch (Exception e) {
            model.addAttribute("error", "Неверный пароль или логин!");
            return "user/login";
        }
    }

}
