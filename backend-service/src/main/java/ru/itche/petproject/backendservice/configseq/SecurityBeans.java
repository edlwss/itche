package ru.itche.petproject.backendservice.configseq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers(HttpMethod.GET,"/musical-school-api/courses",
                                "/musical-school-api/course-subjects/{courseId:\\d+}",
                                "musical-school-api/subjects/**",
                                "musical-school-api/teachers/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"musical-school-api/groups/**",
                                "/musical-school-api/lessons",
                                "musical-school-api/students/student/{studentId:\\d+}",
                                "/musical-school-api/grade/{studentId:\\d+}").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                        .requestMatchers("musical-school-api/lessons/**",
                                "musical-school-api/grade/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/musical-school-api/students/**").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers("/musical-school-api/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Шифрование паролей
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
