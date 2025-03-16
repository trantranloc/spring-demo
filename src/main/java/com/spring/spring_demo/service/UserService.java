package com.spring.spring_demo.service;

import com.spring.spring_demo.model.Role;
import com.spring.spring_demo.model.User;
import com.spring.spring_demo.repository.RoleRepository;
import com.spring.spring_demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    public User saveUser(User user) {
//        Role role = roleRepository.findByRole("ROLE_USER")
//                .orElseGet(() -> {
//                    Role newRole = new Role("ROLE_USER");
//                    return roleRepository.save(newRole);
//                });
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(new HashSet<>(Collections.singleton(role)));
//        return userRepository.save(user);
//    }
    public User getUserById(String id) {
        return userRepository.getById(id);
    }
    public void updateUser(User user) {
         userRepository.save(user);
    }
    @Transactional
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Xóa liên kết trong bảng trung gian
        user.getRoles().clear();

        // Xóa user trực tiếp, không gọi save() trước đó
        userRepository.delete(user);
    }

    public User saveUser(User user) {
        Role role = roleRepository.findByRole("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role("ROLE_USER");
                    return roleRepository.save(newRole);
                });

        // Mã hóa mật khẩu nếu nó chưa được mã hóa
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Gán roles nếu chưa có
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>(Collections.singleton(role)));
        }

        return userRepository.save(user);
    }
}
