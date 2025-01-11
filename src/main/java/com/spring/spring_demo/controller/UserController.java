package com.spring.spring_demo.controller;

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
    @GetMapping("/user-detail")
    public String userDetail(Model model) {
        User user = new User("1", "Loc Tran Tran", "Tran26122003@gmail.com", "0829757417", "Da Nang");
        model.addAttribute("user", user);
        return "user/user_detail";
    }
    @GetMapping("/user-form")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        return "user/user_form";
    }
    @PostMapping("/user-info")
    public String userInformation(@ModelAttribute User user ) {
        System.out.println(user);
        return "redirect:/users/user-form";

    }
    @GetMapping("/update-user")
    public String updateUserForm(Model model) {
        User user = new User("1", "Loc Tran Tran", "Tran26122003@gmail.com", "0829757417", "Da Nang");
        model.addAttribute("user", user);
        return "user/user_update";
    }
    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute User user) {
        System.out.println("Updated User: " + user);
        return "redirect:/users/user-detail";
    }
}
