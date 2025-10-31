package com.devspark.antifakenews.controller;

import com.devspark.antifakenews.entity.User;
import com.devspark.antifakenews.repository.UserRepository;
import com.devspark.antifakenews.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User saved = userService.register(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<User>> list(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}/upgrade")
    public ResponseEntity<User> upgrade(@PathVariable Long id){
        return ResponseEntity.ok(userService.upgradeToAdmin(id));
    }

    @PutMapping("/{id}/make-member")
    public ResponseEntity<User> makeMember(@PathVariable Long id){
        User u = userService.findById(id).orElseThrow();
        u.setRole(User.Role.USER);
        userRepository.save(u);
        return ResponseEntity.ok(u);
    }
}

