package com.example.sistemas.stailenceback.service;

import com.example.sistemas.stailenceback.dto.request.AssignRoleRequest;
import com.example.sistemas.stailenceback.dto.request.CreateBusinessRequest;
import com.example.sistemas.stailenceback.dto.response.BusinessDetailResponse;
import com.example.sistemas.stailenceback.dto.response.BusinessResponse;
import com.example.sistemas.stailenceback.dto.response.AppointmentResponse;
import com.example.sistemas.stailenceback.dto.response.UserResponse;

import java.util.List;

public interface BusinessService {
    BusinessResponse getProfile(Long negocioId);
    BusinessResponse updateProfile(Long negocioId, CreateBusinessRequest req);

    List<UserResponse> listEmployees(Long negocioId);
    UserResponse createEmployee(Long negocioId, UserResponse req);
    List<AppointmentResponse> listAppointments(Long negocioId);
    BusinessDetailResponse getDetail(Long negocioId);
    BusinessResponse createBusiness(CreateBusinessRequest req);

    // Nuevo: listar negocios públicos/básicos
    List<BusinessResponse> listAllBusinesses();

    // Nuevo: asignar/quitar roles en negocio
    UserResponse assignRole(Long negocioId, AssignRoleRequest req);
    void removeRole(Long negocioId, Long usuarioId);
}
