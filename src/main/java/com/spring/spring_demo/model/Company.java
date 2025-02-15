package com.spring.spring_demo.model;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "company")
@Entity
public class Company {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String name;
    private String address;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private List<User> users;

    public List<User > getUsers() {
        return users;
    }

    public void setUsers(List<User > users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String companyName) {
        this.name = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}