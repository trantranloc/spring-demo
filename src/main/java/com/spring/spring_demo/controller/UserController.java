package com.spring.spring_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
