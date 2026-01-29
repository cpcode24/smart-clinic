package com.cpagoui_code.smart_clinic.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final PasswordEncoder passwordEncoder;

    public AdminController(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        super();
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (admin.isPresent() && password != null && passwordEncoder.matches(password, admin.get().getPassword())) {
            log.info("Admin {} logged in", email);
            return "admin/adminDashboard";
        }
        model.addAttribute("error", "Invalid credentials");
        return "admin/adminLogin";
    }
    
    /**
     * Show the registration page for admins.
     */
    @GetMapping("/register")
    public String showRegister() {
        return "admin/adminRegistration";
    }

    /**
     * Register a new admin. If an admin with the same email exists, return it.
     */
    @PostMapping("/register-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin addAdmin(@RequestBody Admin newAdmin, Model model) {
        Optional<Admin> existing = adminRepository.findByEmail(newAdmin.getEmail());
        if (existing.isPresent()) {
            log.info("Admin with email {} already exists", newAdmin.getEmail());
            return existing.get();
        }
        // encode password before saving
        newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        adminRepository.save(newAdmin);
        model.addAttribute("message", "Admin created successfully");
        return newAdmin;
    }

    @DeleteMapping("/{adminId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdmin(@PathVariable Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if (admin.isPresent()) {
            log.info("Deleting admin with ID: {}", adminId);
            adminRepository.delete(admin.get());
        } else {
            log.warn("No admin found with ID: {}", adminId);
            throw new NotFoundException("Admin with ID " + adminId + " not found");
        }
    }

    @PatchMapping("/{id}/update-password")
    @ResponseStatus(HttpStatus.OK)
    public boolean updatePassword(@RequestBody Map<String, String> passwords, @PathVariable Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) return false;
        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");
        if (!passwordEncoder.matches(oldPassword, admin.getPassword())) {
            return false;
        }
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);
        return true;
    }

    @PostMapping("/{id}/check-password")
    @ResponseStatus(HttpStatus.OK)
    public boolean isPassword(@RequestBody String password, @PathVariable Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) return false;
        return passwordEncoder.matches(password, admin.getPassword());
    }
    
    @GetMapping({"", "/"})
    public String root() {
        // Redirect bare /patients to the login page
        return "redirect:/admin/login";
    }
}
