package com.spring.spring_demo.restcontroller;

import com.spring.spring_demo.model.Company;
import com.spring.spring_demo.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {
    private final CompanyService companyService;

    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("{id}")
    public Optional<Company> getCompanyById(@PathVariable String id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping()
    public Company createCompany(@RequestBody Company company) {
        if (company.getId() != null && companyService.getCompanyById(company.getId()).isPresent()) {
            return null;
        }
        return companyService.saveCompany(company);
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable String id) {
        Optional<Company> company = companyService.getCompanyById(id);
        if (company.isPresent()) {
            companyService.deleteCompany(id);
        }
        return "Delete Complete";

    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable String id, @RequestBody Company company) {
        Optional<Company> companyExists = companyService.getCompanyById(id);
        if (companyExists.isPresent()) {
            Company existingCompany = companyExists.get();
            existingCompany.setName(company.getName());
            existingCompany.setEmail(company.getEmail());
            existingCompany.setAddress(company.getAddress());
            return companyService.saveCompany(existingCompany);
        }
        return null;
    }
    @PostMapping("/add-user")
    public String addUserToCompany(@RequestParam String companyId, @RequestParam String userId) {
         companyService.addUserToCompany(companyId, userId);
         return "Add User Complete";
    }

}
