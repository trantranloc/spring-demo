package com.spring.spring_demo.service;

import com.spring.spring_demo.model.User;
import com.spring.spring_demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private User user;
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        this.user = user;
    }
    public User getUserById(String id) {
        return userRepository.getById(id);
    }
    public void updateUser(User user) {
         userRepository.save(user);
    }
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
