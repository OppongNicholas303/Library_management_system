package org.example.library_management_system.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    Validation validation;

    @BeforeEach
    void setUp() {
        validation = new Validation();
    }

    @ParameterizedTest
    @ValueSource(strings = {"vida@gmail.com", "admin@gmail.com"})
    void testValidContact(String email) {
        boolean results = validation.isValidEmail(email);
        assertTrue(results);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0243844557", "0207384748"})
    void testInvalidContact(String contact) {
        boolean results = validation.isValidContact(contact);
        assertTrue(results);
    }

}