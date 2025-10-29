package com.devspark.antifakenews.controller;

import com.devspark.antifakenews.entity.User;
import com.devspark.antifakenews.repository.UserRepository;
import com.devspark.antifakenews.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    @Operation(summary="Register new user")
    public ResponseEntity<User> register(@Valid @RequestBody User userRequest){
        // basic: server sets role & active
        User u = userService.register(userRequest);
        return ResponseEntity.ok(u);
    }

    @GetMapping
    @Operation(summary="List users (admin)")
    public ResponseEntity<List<User>> listUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}/upgrade")
    @Operation(summary="Upgrade user to ADMIN")
    public ResponseEntity<User> upgrade(@PathVariable Long id){
        User u = userService.upgradeToAdmin(id);
        return ResponseEntity.ok(u);
    }

    @PutMapping("/{id}/downgrade")
    @Operation(summary="Downgrade user to USER")
    public ResponseEntity<User> downgrade(@PathVariable Long id){
        User u = userService.downgradeToUser(id);
        return ResponseEntity.ok(u);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Delete user")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

