# Advanced Library Management System

## Introduction

The Advanced Library Management System is designed to help librarians manage library resources and member activities efficiently. The system allows librarians to register patrons, add books and magazines, borrow and return items, and reserve items for members. It is built using Java, JDBC, and JavaFX, with a focus on security, ease of use, and reliable performance. This document outlines the key features and requirements needed to develop the system.

---

## 1. Functional Requirements

### a. Entities and Class Structure:

- **LibraryItem Class:**
  - Base class for all library materials (Book, Magazine).
  - **Attributes:** 
    - `ID`, `title`, `author`, `publicationDate`, `status` (available/borrowed/reserved).
  - **Methods:** 
    - `checkAvailability()`, `updateStatus()`, `displayInfo()`.

- **Book Class:**
  - Inherits from LibraryItem.
  - **Additional Attributes:** 
    - `ISBN`, `publicationYear`.

- **Magazine Class:**
  - Inherits from LibraryItem.
  - **Additional Attributes:** 
    - `issueNumber`.

- **Patron Class:**
  - **Attributes:** 
    - `ID`, `name`, `contactInformation`, `email`.
  - **Methods:** 
    - `borrowItem()`, `returnItem()`, `reserveItem()`.

- **Librarian Class:**
  - Inherits from Patron.
  - **Additional Attributes:** 
    - `password`.
  - **Methods:** 
    - `login()`, `signUp()`, `registerPatron()`, `addLibraryItem()`, `borrowItem()`, `reserveItem()`.

- **Transaction Class:**
  - **Attributes:** 
    - `transactionID`, `patronID`, `itemID`, `borrowDate`, `returnDate`.
  - **Methods:** 
    - `createTransaction()`, `updateTransactionStatus()`.

- **Reservation Class:**
  - **Attributes:** 
    - `reservationID`, `patronID`, `itemID`, `reservationDate`.
  - **Methods:** 
    - `createReservation()`.

---

### b. Functional System Features:

- **Librarian SignUp and Login:**
  - Only the librarian can sign up and log in to the system.
  - Login should be authenticated using email and password.

- **Patron Registration:**
  - The librarian can register new patrons, converting them into library members.
  - Each patron will have a unique ID and personal information stored in the system.

- **Borrowing Library Items:**
  - The librarian can allow a patron to borrow books or magazines.
  - Borrowed items should be marked as unavailable until returned.
  - Patrons can borrow multiple items at a time, subject to availability.

- **Adding Library Items:**
  - The librarian can add new books or magazines to the library’s inventory.
  - Books and magazines must have all necessary attributes (e.g., title, author, publication date).

- **Reserving Library Items:**
  - The librarian can reserve books or magazines for patrons.
  - A reservation should mark the item as reserved and assign a patron.

- **Transaction Management:**
  - All borrowing activities should be recorded as transactions (with dates and item status).
  - The system must track borrowed and returned items, showing when a transaction is completed.

- **Reservation Management:**
  - Reservations can be made by the librarian for items that are unavailable.
  - Reservations should have a status to indicate whether they are active or cancelled.

---

## 2. Non-Functional Requirements

### a. Usability:
- The system should have an intuitive and easy-to-use JavaFX interface for the librarian and patrons.
- The librarian should be able to manage all operations (sign up, login, register patrons, borrow, add, reserve items) efficiently.

### b. Performance:
- The system should handle multiple patrons borrowing and reserving items simultaneously with minimal performance delay.

### c. Security:
- The librarian’s login credentials should be securely stored, and login should require authentication.
- Only authenticated librarians can access sensitive operations (e.g., patron registration, borrowing items).

### d. Maintainability:
- The code should be modular with clear separation of concerns (e.g., database interaction, user interface, business logic).

### e. Compatibility:
- The system should be compatible with major RDBMS (e.g., MySQL, PostgreSQL) to store data for patrons, books, and transactions.
- The system should be operable on standard desktop environments with JavaFX.
