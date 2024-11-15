package org.example.library_management_system.dto;

public record LibrarianDto(
        int librarianId,
        String firstName,
        String lastName,
        String email
) {
}
