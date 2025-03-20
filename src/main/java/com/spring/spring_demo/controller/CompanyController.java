package com.spring.spring_demo.controller;

import com.spring.spring_demo.model.Company;
import com.spring.spring_demo.service.CompanyService;
import com.spring.spring_demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;

    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }


    // Hiển thị danh sách công ty
    @GetMapping
    public String listCompanies(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "company/list";
    }

    // Hiển thị form thêm công ty
    @GetMapping("/add")
    public String showAddCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/add_form";
    }

    // Thêm công ty mới
    @PostMapping("/add")
    public String addCompany(@ModelAttribute("company") Company company) {
        companyService.saveCompany(company);
        return "redirect:/companies";
    }

    // Hiển thị form chỉnh sửa công ty
    @GetMapping("/edit/{id}")
    public String showEditCompanyForm(@PathVariable String id, Model model) {
        Optional<Company> company = companyService.getCompanyById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            return "company/edit_form";
        }
        return "redirect:/companies";
    }

    @PostMapping("/edit/{id}")
    public String updateCompany(@PathVariable String id, @ModelAttribute("company") Company updatedCompany) {
        Optional<Company> existingCompany = companyService.getCompanyById(id);
        updatedCompany.setUsers(existingCompany.get().getUsers());
        updatedCompany.setId(id);
        companyService.saveCompany(updatedCompany);

        return "redirect:/companies";
    }

    // Xóa công ty
    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }

    // Xem chi tiết
    @GetMapping("/{id}")
    public String companyDetail(@PathVariable String id, Model model) {
        Optional<Company> company = companyService.getCompanyById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            model.addAttribute("users", userService.getAllUsers());
            return "company/detail";
        }
        return "redirect:/companies";
    }

    // Thêm user vào công ty
    @PostMapping("/{companyId}/add-user")
    public String addUserToCompany(@PathVariable String companyId, @RequestParam String userId) {
        companyService.addUserToCompany(companyId, userId);
        return "redirect:/companies/" + companyId;
    }
}
