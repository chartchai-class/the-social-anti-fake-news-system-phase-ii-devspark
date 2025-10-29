package com.devspark.antifakenews.service;

import com.devspark.antifakenews.entity.User;
import com.devspark.antifakenews.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User register(User user){
        // Basic: if username/email exists, throw
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        user.setRole(User.Role.USER);
        user.setActive(true);
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id){ return userRepository.findById(id); }

    public List<User> findAll(){ return userRepository.findAll(); }

    public User upgradeToAdmin(Long id){
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        u.setRole(User.Role.ADMIN);
        return userRepository.save(u);
    }

    public User downgradeToUser(Long id){
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        u.setRole(User.Role.USER);
        return userRepository.save(u);
    }

    public void deleteUser(Long id){ userRepository.deleteById(id); }
}


