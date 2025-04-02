package com.flavioramses.huellitasbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        log.info("Recibida solicitud de estado del servidor");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("mensaje", "El servidor está funcionando correctamente");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
} 