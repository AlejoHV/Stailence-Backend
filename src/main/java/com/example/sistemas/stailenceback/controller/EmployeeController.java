package com.example.sistemas.stailenceback.controller;

import com.example.sistemas.stailenceback.dto.response.AppointmentResponse;
import com.example.sistemas.stailenceback.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final AppointmentService appointmentService;

    public EmployeeController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Obtener agenda")
    @GetMapping("/schedule")
    public ResponseEntity<List<AppointmentResponse>> schedule(@RequestParam Long empleadoId, @RequestParam String fecha) {
        // Implementación básica pendiente
        return ResponseEntity.ok(List.of());
    }

    @Operation(summary = "Actualizar disponibilidad")
    @PutMapping("/availability")
    public ResponseEntity<Void> availability(@RequestParam Long empleadoId) {
        // Implementación pendiente
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Citas del día")
    @GetMapping("/today-appointments")
    public ResponseEntity<List<AppointmentResponse>> todayAppointments(@RequestParam Long empleadoId) {
        return ResponseEntity.ok(List.of());
    }
}

