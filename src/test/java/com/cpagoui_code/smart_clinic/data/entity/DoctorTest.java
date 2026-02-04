package com.cpagoui_code.smart_clinic.data.entity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Doctor entity.
 */
public class DoctorTest {

    @Test
    public void testSettersAndGetters() {
        Doctor d = new Doctor();
        d.setDoctorId(42L);
        d.setName("Dr. Alice");
        d.setSpecialty("Cardiology");
        d.setEmail("alice@example.com");
        d.setPhone("1234567890");

        assertEquals(42L, d.getDoctorId());
        assertEquals("Dr. Alice", d.getName());
        assertEquals("Cardiology", d.getSpecialty());
        assertEquals("alice@example.com", d.getEmail());
        assertEquals("1234567890", d.getPhone());
    }

    @Test
    public void testCollections() {
        Doctor d = new Doctor();
        List<String> times = new ArrayList<>();
        times.add("09:00");
        times.add("10:00");
        d.setAvailableTimes(times);

        assertNotNull(d.getAvailableTimes());
        assertEquals(2, d.getAvailableTimes().size());
        assertEquals("09:00", d.getAvailableTimes().get(0));

        List<Appointment> appts = new ArrayList<>();
        // Appointment is a domain object, not fully populated here
        d.setAppointments(appts);
        assertSame(appts, d.getAppointments());
    }


}