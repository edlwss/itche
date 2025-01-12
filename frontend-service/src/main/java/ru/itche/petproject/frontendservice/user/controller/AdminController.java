package ru.itche.petproject.frontendservice.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itche.petproject.frontendservice.user.client.UserRestClient;

@Controller
@RequestMapping("/musical-school/admin/{adminId:\\d+}")
@RequiredArgsConstructor
public class AdminController {

    private final UserRestClient userRestClient;
    private  final HttpSession session;

    @ModelAttribute("role")
    public String getRole() {
        return this.session.getAttribute("role").toString();
    }

    @GetMapping
    public String findAdmin(@PathVariable("adminId") Integer adminId, Model model) {
        model.addAttribute("admin", userRestClient.findAdmin(adminId).orElseThrow());
        return "user/admin_prof";
    }


}
