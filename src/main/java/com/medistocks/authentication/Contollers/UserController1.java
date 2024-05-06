package com.medistocks.authentication.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medistocks.authentication.Entity.UserModel;
import com.medistocks.authentication.Service.UserService1;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController1 {

    @Autowired
    private UserService1 userService1;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService1.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long userId) {
        Optional<UserModel> user = userService1.getUserById(userId);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UserModel> saveUser(@RequestBody UserModel user) {
        UserModel newUser = userService1.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long userId, @RequestBody UserModel user) {
        UserModel updatedUser = userService1.updateUser(userId, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService1.deleteUser(userId);
        return "User details deleted";
    }
}
