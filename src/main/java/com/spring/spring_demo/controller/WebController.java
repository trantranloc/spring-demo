package com.spring.spring_demo.controller;

import com.spring.spring_demo.model.User;
import com.spring.spring_demo.repository.RoleRepository;
import com.spring.spring_demo.repository.UserRepository;
import com.spring.spring_demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;

@Controller
public class WebController {

    private final UserService userService;
    private final UserRepository userRepository;

    public WebController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("user") User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return "register";
        }
        Optional<User> existUser = userRepository.findByUsername(user.getUsername());
        if (existUser.isPresent()) {
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

}
