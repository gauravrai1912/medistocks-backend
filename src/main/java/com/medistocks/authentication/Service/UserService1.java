package com.medistocks.authentication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medistocks.authentication.Entity.UserModel;
import com.medistocks.authentication.Repository.UserRepository1;

import java.util.List;
import java.util.Optional;

@Service
public class UserService1 {

    @Autowired
    private UserRepository1 userRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserModel updateUser(Long userId, UserModel user) {
        UserModel existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setFullName(user.getFullName());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        
        return userRepository.save(existingUser);
    }
}
