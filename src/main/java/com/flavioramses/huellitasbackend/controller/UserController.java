package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.flavioramses.huellitasbackend.model.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{Id}")
    public Optional<User> getUserById(@PathVariable("Id") Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @DeleteMapping("/{Id}")
    public void deleteUserById(@PathVariable("Id") Long userId) {
        userService.deleteUserById(userId);
    }


}
