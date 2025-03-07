package com.spring.spring_demo.restcontroller;

import com.spring.spring_demo.model.Company;
import com.spring.spring_demo.service.CompanyService;
import com.spring.spring_demo.service.UserService;
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
    public List<Company> getAllCompanies(){
        return companyService.getAllCompanies();
    }
    @PostMapping()
    public Company createCompany(@RequestBody Company company) {
        return companyService.saveCompany(company);
    }
    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);
        return "Delete Complete";
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
            return ResponseEntity.notFound().build();
        }
    }

}
