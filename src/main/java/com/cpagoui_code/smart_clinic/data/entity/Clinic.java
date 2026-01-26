
/*************************************************************************
 *   Clinic.java                               
 *   Author : Constant Pagoui                         
 *   Date   : 2026-01-05                        
 *                                              
 *   Summary:                                   
 *   Represents a medical clinic with contact and location details and the list of       
 *   associated doctors.                        
 * **************************************************************************/


package com.cpagoui_code.smart_clinic.data.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;
    @NotNull
    private String clinicName;
    @NotNull
    @Size(min = 10, max = 100)
    private String address;

    @NotNull
    private String phoneNumber;

    private int zipCode;

    @Size(min = 3, max = 100)
    @NotNull
    private String city;

    @NotNull
    private String state;

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
}
