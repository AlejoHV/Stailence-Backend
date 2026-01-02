package com.example.sistemas.stailenceback.controller;

import com.example.sistemas.stailenceback.dto.request.CreateAppointmentRequest;
import com.example.sistemas.stailenceback.dto.response.AppointmentResponse;
import com.example.sistemas.stailenceback.dto.response.BusinessResponse;
import com.example.sistemas.stailenceback.dto.response.ServiceResponse;
import com.example.sistemas.stailenceback.service.AppointmentService;
import com.example.sistemas.stailenceback.service.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final AppointmentService appointmentService;
    private final BusinessService businessService;

    public ClientController(AppointmentService appointmentService, BusinessService businessService) {
        this.appointmentService = appointmentService;
        this.businessService = businessService;
    }

    @Operation(summary = "Listar negocios (básico)")
    @GetMapping("/businesses")
    public ResponseEntity<List<BusinessResponse>> getBusinesses() {
        return ResponseEntity.ok(businessService.listAllBusinesses());
    }

    @Operation(summary = "Listar servicios de un negocio")
    @GetMapping("/services/{businessId}")
    public ResponseEntity<List<ServiceResponse>> getServices(@PathVariable Long businessId) {
        var detail = businessService.getDetail(businessId);
        return ResponseEntity.ok(detail.getServicios());
    }

    @Operation(summary = "Crear cita")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody CreateAppointmentRequest req) {
        return ResponseEntity.ok(appointmentService.createAppointment(req));
    }

    @Operation(summary = "Obtener mis citas")
    @GetMapping("/my-appointments")
    public ResponseEntity<List<AppointmentResponse>> myAppointments(@RequestParam Long clienteId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByClient(clienteId));
    }
}
