package com.cpagoui_code.smart_clinic.data.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 100)
    private String name;
    @Email
    @Size(min = 3, max = 100)
    private String email;
    @NotNull(message = "Username cannot be null")
    private String username;
    @Size(min = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;
    @Pattern(regexp = "\\d{10}")
    private String phone;
    @Size(max = 255)
    private String address;

}
