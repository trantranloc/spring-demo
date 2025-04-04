package com.spring.spring_demo.restcontroller;

import com.spring.spring_demo.model.User;
import com.spring.spring_demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserRestController {


    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "Delete Complete";
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        User userExists = userService.getUserById(id);
        if (userExists != null) {
            userExists.setName(user.getName());
            userExists.setEmail(user.getEmail());
            userExists.setUsername(user.getUsername());
            userExists.setAddress(user.getAddress());
            userExists.setPhone(user.getPhone());
            userService.saveUser(userExists);
        }
        return userExists;
    }
}
