package com.spring.spring_demo.controller;

import com.spring.spring_demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user-detail/{userId}")
    public String userDetail(Model model, @PathVariable String userId) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/user_detail";
    }

    @GetMapping("/update-user/{userId}")
    public String updateUserForm(Model model,@PathVariable String userId) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/user_update";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute User user) {
        User userExists = userService.getUserById(user.getId());
        userExists.setName(user.getName());
        userExists.setEmail(user.getEmail());
        userExists.setAddress(user.getAddress());
        userExists.setPhone(user.getPhone());
        userService.saveUser(userExists);
        return "redirect:/users/user-detail";
    }
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
