package com.spring.spring_demo.controller;

import com.spring.spring_demo.model.Role;
import com.spring.spring_demo.repository.RoleRepository;
import com.spring.spring_demo.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/list-user")
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
        if (user.getId() == null) {
            return "redirect:/users/list-user";
        }
        return "redirect:/users/detail/" + user.getId();

    }

    @GetMapping("/detail/{userId}")
    public String userDetail(Model model, @PathVariable String userId) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/user_detail";
    }

    @GetMapping("/edit/{userId}")
    public String updateUserForm(Model model, @PathVariable String userId) {
        List<Role> roles = roleRepository.findAll();
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user/user_update";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user) {

        User userExists = userService.getUserById(user.getId());
        userExists.setName(user.getName());
        userExists.setEmail(user.getEmail());
        userExists.setAddress(user.getAddress());
        userExists.setPhone(user.getPhone());
        userService.updateUser(userExists);
        return "redirect:/users/detail/" + user.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "redirect:/users/list-user";
    }


}