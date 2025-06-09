package org.gerasic.gateway.controller;

import jakarta.validation.Valid;
import org.gerasic.gateway.dto.AccountResponse;
import org.gerasic.gateway.dto.CreateAdminRequest;
import org.gerasic.gateway.dto.CreateUserRequest;
import org.gerasic.gateway.dto.UserResponse;
import org.gerasic.gateway.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest user) {
        adminService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/admins")
    public ResponseEntity<Void> createAdmin(@Valid @RequestBody CreateAdminRequest user) {
        adminService.createAdmin(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String hairColor) {
        List<UserResponse> users = adminService.getAllUsers(gender, hairColor);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<UserResponse> getUserByLogin(@PathVariable String login) {
        return adminService.getUserByLogin(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(adminService.getAllAccounts());
    }

    @GetMapping("/accounts/user/{login}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserLogin(@PathVariable String login) {
        return ResponseEntity.ok(adminService.getAccountsByUserLogin(login));
    }
}
