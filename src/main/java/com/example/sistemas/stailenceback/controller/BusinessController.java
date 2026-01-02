package com.example.sistemas.stailenceback.controller;

import com.example.sistemas.stailenceback.dto.request.AssignRoleRequest;
import com.example.sistemas.stailenceback.dto.request.CreateBusinessRequest;
import com.example.sistemas.stailenceback.dto.response.*;
import com.example.sistemas.stailenceback.service.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN') or @negocioSecurity.isOwner(authentication, #id) or @negocioSecurity.hasRole(authentication, #id, 'MANAGER')")
    @PutMapping("/profile")
    public ResponseEntity<BusinessResponse> updateProfile(@RequestParam Long id, @Valid @RequestBody CreateBusinessRequest req) {
        return ResponseEntity.ok(businessService.updateProfile(id, req));
    }

    @Operation(summary = "Listar empleados del negocio")
    @PreAuthorize("hasRole('ADMIN') or @negocioSecurity.isOwner(authentication, #id) or @negocioSecurity.hasRole(authentication, #id, 'MANAGER')")
    @GetMapping("/employees")
    public ResponseEntity<List<UserResponse>> getEmployees(@RequestParam Long id) {
        return ResponseEntity.ok(businessService.listEmployees(id));
    }

    @Operation(summary = "Crear empleado")
    @PreAuthorize("hasRole('ADMIN') or @negocioSecurity.isOwner(authentication, #id) or @negocioSecurity.hasRole(authentication, #id, 'MANAGER')")
    @PostMapping("/employees")
    public ResponseEntity<UserResponse> createEmployee(@RequestParam Long id, @Valid @RequestBody UserResponse req) {
        return ResponseEntity.ok(businessService.createEmployee(id, req));
    }

    @Operation(summary = "Listar citas del negocio")
    @PreAuthorize("hasRole('ADMIN') or @negocioSecurity.isOwner(authentication, #id) or @negocioSecurity.hasRole(authentication, #id, 'MANAGER')")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointments(@RequestParam Long id) {
        return ResponseEntity.ok(businessService.listAppointments(id));
    }

    @Operation(summary = "Detalle completo del negocio")
    @GetMapping("/detail")
    public ResponseEntity<BusinessDetailResponse> getDetail(@RequestParam Long id) {
        return ResponseEntity.ok(businessService.getDetail(id));
    }

    @Operation(summary = "Crear negocio")
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<BusinessResponse> createBusiness(@Valid @RequestBody CreateBusinessRequest req) {
        BusinessResponse resp = businessService.createBusiness(req);
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Asignar rol a usuario en negocio")
    @PreAuthorize("hasRole('ADMIN') or @negocioSecurity.isOwner(authentication, #id)")
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserResponse> assignRole(@PathVariable Long id, @Valid @RequestBody AssignRoleRequest req) {
        return ResponseEntity.ok(businessService.assignRole(id, req));
    }

    @Operation(summary = "Remover rol de usuario en negocio")
    @PreAuthorize("hasRole('ADMIN') or @negocioSecurity.isOwner(authentication, #id)")
    @DeleteMapping("/{id}/roles")
    public ResponseEntity<Void> removeRole(@PathVariable Long id, @RequestParam Long usuarioId) {
        businessService.removeRole(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
