package com.spring.spring_demo.service;

import com.spring.spring_demo.model.Company;
import com.spring.spring_demo.model.User;
import com.spring.spring_demo.repository.CompanyRepository;
import com.spring.spring_demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    public CompanyService(CompanyRepository companyRepository,UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(String id) {
        return companyRepository.findById(id);
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public void deleteCompany(String id) {
        companyRepository.deleteById(id);
    }
    public void addUserToCompany(String companyId, String userId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (company != null && user != null) {
            company.getUsers().add(user);
            companyRepository.save(company);
        }
    }

}
