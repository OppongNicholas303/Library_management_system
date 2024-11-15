package org.example.library_management_system.utils;

public class Validation {


    // Validate contact (for example, 10-15 digits, numbers only)
    public boolean isValidContact(String contact) {
        return contact.matches("\\d{10,15}");
    }

    // Validate email (basic email pattern)
    public boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }
}
