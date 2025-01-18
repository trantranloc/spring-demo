package com.spring.spring_demo.repository;

import com.spring.spring_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
