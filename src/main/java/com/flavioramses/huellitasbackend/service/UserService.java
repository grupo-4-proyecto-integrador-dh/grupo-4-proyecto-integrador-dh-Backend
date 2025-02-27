package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.model.User;
import com.flavioramses.huellitasbackend.model.UserRole;
import com.flavioramses.huellitasbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRol(role);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    
    public void assignRole(Long userId, UserRole newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setRol(newRole);
        userRepository.save(user);
    }

    public void removeAdminRole(Long userId) {
        assignRole(userId, UserRole.USER);
    }
}
