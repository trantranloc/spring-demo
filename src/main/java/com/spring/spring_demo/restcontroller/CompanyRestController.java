package com.spring.spring_demo.restcontroller;

import com.spring.spring_demo.model.Company;
import com.spring.spring_demo.service.CompanyService;
import com.spring.spring_demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {
    private final CompanyService companyService;
    private final UserService userService;

    public CompanyRestController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        if (companies.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có dữ liệu
        }
        return ResponseEntity.ok(companies);
    }

    @PostMapping()
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        if (company.getId() != null && companyService.getCompanyById(company.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Tránh trùng lặp
        }
        Company savedCompany = companyService.saveCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable String id) {
        Optional<Company> company = companyService.getCompanyById(id);
        if (company.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
        }
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Delete Complete");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable String id, @RequestBody Company company) {
        Optional<Company> companyExists = companyService.getCompanyById(id);
        if (companyExists.isPresent()) {
            Company existingCompany = companyExists.get();
            existingCompany.setName(company.getName());
            existingCompany.setEmail(company.getEmail());
            existingCompany.setAddress(company.getAddress());
            companyService.saveCompany(existingCompany);
            return ResponseEntity.ok(existingCompany);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
