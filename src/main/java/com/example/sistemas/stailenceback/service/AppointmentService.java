package com.example.sistemas.stailenceback.service;

import com.example.sistemas.stailenceback.dto.request.CreateAppointmentRequest;
import com.example.sistemas.stailenceback.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(CreateAppointmentRequest req);
    List<AppointmentResponse> getAppointmentsByClient(Long clienteId);
}

