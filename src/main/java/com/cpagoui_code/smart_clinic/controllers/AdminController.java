package com.cpagoui_code.smart_clinic.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cpagoui_code.smart_clinic.data.entity.Admin;
import com.cpagoui_code.smart_clinic.data.repository.AdminRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        super();
        this.adminRepository = adminRepository;
    }

    @GetMapping("/{adminId}")
    @ResponseStatus(HttpStatus.OK)
    public Admin getAdminById(@PathVariable Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("Admin not found"));
    }

    @GetMapping("/admin")
    public String getMethodName(@RequestParam String param) {
        return "admin/adminLogin";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "admin/adminLogin";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && password != null && password.equals(admin.get().getPassword())) {
            log.info("Admin {} logged in", email);
            return "admin/adminDashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "admin/adminLogin";
    }
    
    @GetMapping({"", "/"})
    public String root() {
        // Redirect bare /patients to the login page
        return "redirect:/admin/login";
    }
}
