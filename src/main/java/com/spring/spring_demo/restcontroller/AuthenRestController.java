package com.spring.spring_demo.restcontroller;

import com.spring.spring_demo.dto.AuthRequest;
import com.spring.spring_demo.filter.JwtService;
import com.spring.spring_demo.model.Role;
import com.spring.spring_demo.model.User;
import com.spring.spring_demo.repository.UserRepository;
import com.spring.spring_demo.service.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AuthenRestController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthenRestController(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/welcome")
    public ResponseEntity<Map<String, String>> welcome() {
        return ResponseEntity.ok(Map.of("message", "Welcome, this endpoint is not secure"));
    }

    @GetMapping("/user/userProfile")
    public ResponseEntity<Map<String, String>> userProfile() {
        return ResponseEntity.ok(Map.of("message", "Welcome to User Profile"));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> processRegister(@RequestBody AuthRequest userRequest) {
        // Mã hóa mật khẩu
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        // Tạo user mới
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(encodedPassword);
        Set<Role> roles = new HashSet<>(List.of(new Role("ROLE_USER")));
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Register success"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername());
                return ResponseEntity.ok(Map.of(
                        "message", "Authentication successful",
                        "token", token
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                    "message", "Invalid credentials",
                    "error", e.getMessage()
            ));
        }

        return ResponseEntity.status(401).body(Map.of("message", "Authentication failed"));
    }
    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        boolean isValid = jwtService.validateToken(token, userDetails);
        return ResponseEntity
                .status(isValid ? 200 : 401)
                .body(Map.of("message", isValid ? "Token hợp lệ" : "Token không hợp lệ"));
    }
}
