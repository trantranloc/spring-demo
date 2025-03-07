package com.spring.spring_demo.config;

import com.spring.spring_demo.model.User;
import com.spring.spring_demo.repository.UserRepository;
import com.spring.spring_demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Optional;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

        @Bean
        UserDetailsService userDetailsService() {
                return new CustomUserDetailsService();
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, UserRepository userRepository) throws Exception {
                http
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/users/list-user", "/users/add-form", "/users/add", "/users/delete/**").hasRole("ADMIN") // Chỉ ADMIN được phép quản lý user
                                .requestMatchers( "/users/edit/**","/users/detail/**").authenticated()
                                .requestMatchers("/login","/register","/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                        ).csrf(AbstractHttpConfigurer::disable)
                        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                        .formLogin(form -> form
                                .loginPage("/login")
                                .successHandler((request, response, authentication) -> {
                                        boolean isAdmin = authentication.getAuthorities().stream()
                                                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

                                        String username = authentication.getName();
                                        Optional<User> user = userRepository.findByUsername(username);

                                        if (isAdmin) {
                                                response.sendRedirect("/users/list-user");
                                        } else if (user.isPresent()) {
                                                response.sendRedirect("/users/detail/" + user.get().getId());
                                        } else {
                                                response.sendRedirect("/login?error");
                                        }
                                })
                                .permitAll()

                        )
                        .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                        )
                        .userDetailsService(userDetailsService());
                return http.build();
        }
}
