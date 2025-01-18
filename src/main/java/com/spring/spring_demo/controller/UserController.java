package com.spring.spring_demo.controller;

import com.spring.spring_demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.spring_demo.model.User;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-form")
    public String userForm(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "user/user_form";
    }

    @PostMapping("/user-info")
    public String userInformation(@ModelAttribute User user) {
        userService.saveUser(user);
        System.out.println("User: " + user);
        return "redirect:/users/user-detail";
    }

    @GetMapping("/user-detail")
    public String userDetail(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "user/user_detail";
    }

    @GetMapping("/update-user")
    public String updateUserForm(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "user/user_update";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute User user) {
        userService.saveUser(user);
        System.out.println("Updated User: " + user);
        return "redirect:/users/user-detail";
    }
}
