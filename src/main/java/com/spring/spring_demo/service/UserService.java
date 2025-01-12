package com.spring.spring_demo.service;

import com.spring.spring_demo.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private User user;

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void saveUser(User user) {
        this.user = user;
    }
}
