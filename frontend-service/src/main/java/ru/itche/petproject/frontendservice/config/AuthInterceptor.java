package ru.itche.petproject.frontendservice.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = (String) request.getSession().getAttribute("token");
        String role = (String) request.getSession().getAttribute("role");

        if (token == null) {
            response.sendRedirect("/login");
            return false;
        }

        // Можно добавить дополнительные проверки на основе роли
        return true;
    }
}
