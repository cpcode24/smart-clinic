package com.cpagoui_code.smart_clinic.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cpagoui_code.smart_clinic.data.entity.Admin;
import com.cpagoui_code.smart_clinic.data.repository.AdminRepository;
import com.cpagoui_code.smart_clinic.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
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
    public Admin getAdminById(@RequestBody UUID adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("Admin not found"));
    }
}
