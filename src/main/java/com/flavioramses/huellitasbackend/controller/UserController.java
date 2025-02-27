package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.model.User;
import com.flavioramses.huellitasbackend.model.UserRole;
import com.flavioramses.huellitasbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/rol/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRole role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @PutMapping("/{userId}/rol/{role}")
    public ResponseEntity<String> assignRole(@PathVariable Long userId, @PathVariable UserRole role) {
        userService.assignRole(userId, role);
        return ResponseEntity.ok("Rol actualizado correctamente.");
    }

    @PutMapping("/{userId}/revocar-admin")
    public ResponseEntity<String> removeAdminRole(@PathVariable Long userId) {
        userService.removeAdminRole(userId);
        return ResponseEntity.ok("Permiso de administrador revocado correctamente.");
    }
}

