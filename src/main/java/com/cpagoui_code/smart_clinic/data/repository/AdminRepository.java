package com.cpagoui_code.smart_clinic.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpagoui_code.smart_clinic.data.entity.Admin;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    
}
