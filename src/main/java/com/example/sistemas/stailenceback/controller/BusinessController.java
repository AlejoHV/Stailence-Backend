package com.example.sistemas.stailenceback.controller;

import com.example.sistemas.stailenceback.dto.request.CreateBusinessRequest;
import com.example.sistemas.stailenceback.dto.response.*;
import com.example.sistemas.stailenceback.service.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @Operation(summary = "Obtener perfil de negocio")
    @GetMapping("/profile")
    public ResponseEntity<BusinessResponse> getProfile(@RequestParam Long id) {
        return ResponseEntity.ok(businessService.getProfile(id));
    }

    @Operation(summary = "Actualizar perfil de negocio")
    @PutMapping("/profile")
    public ResponseEntity<BusinessResponse> updateProfile(@RequestParam Long id, @Valid @RequestBody CreateBusinessRequest req) {
        return ResponseEntity.ok(businessService.updateProfile(id, req));
    }

    @Operation(summary = "Listar empleados del negocio")
    @GetMapping("/employees")
    public ResponseEntity<List<UserResponse>> getEmployees(@RequestParam Long id) {
        return ResponseEntity.ok(businessService.listEmployees(id));
    }

    @Operation(summary = "Crear empleado")
    @PostMapping("/employees")
    public ResponseEntity<UserResponse> createEmployee(@RequestParam Long id, @Valid @RequestBody UserResponse req) {
        return ResponseEntity.ok(businessService.createEmployee(id, req));
    }

    @Operation(summary = "Listar citas del negocio")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointments(@RequestParam Long id) {
        return ResponseEntity.ok(businessService.listAppointments(id));
    }

    @Operation(summary = "Detalle completo del negocio")
    @GetMapping("/detail")
    public ResponseEntity<BusinessDetailResponse> getDetail(@RequestParam Long id) {
        return ResponseEntity.ok(businessService.getDetail(id));
    }
}
