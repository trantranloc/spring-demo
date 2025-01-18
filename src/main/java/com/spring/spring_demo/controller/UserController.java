package com.spring.spring_demo.controller;

import com.spring.spring_demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.spring.spring_demo.model.User;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        System.out.println(users);
        return "user/list_user";
    }


    @GetMapping("/add-form")
    public String addFormUser() {
        return "user/user_form";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        System.out.println("User: " + user);
        return "redirect:/users";
    }

    @GetMapping("/detail/{userId}")
    public String userDetail(Model model, @PathVariable String userId) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/user_detail";
    }

    @GetMapping("/edit/{userId}")
    public String updateUserForm(Model model,@PathVariable String userId) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/user_update";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user) {
        if (user.getId() == null) {
            // Xử lý lỗi, có thể thêm thông báo hoặc log
            return "redirect:/users?error=InvalidUserId";
        }
        User userExists = userService.getUserById(user.getId());
        if (userExists == null) {
            // Xử lý khi không tìm thấy người dùng
            return "redirect:/users?error=UserNotFound";
        }
        userExists.setName(user.getName());
        userExists.setEmail(user.getEmail());
        userExists.setAddress(user.getAddress());
        userExists.setPhone(user.getPhone());
        userService.saveUser(userExists);
        return "redirect:/users";
    }
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
