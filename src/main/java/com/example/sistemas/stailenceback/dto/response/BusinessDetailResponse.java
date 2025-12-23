package com.example.sistemas.stailenceback.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessDetailResponse {
    private Long id;
    private String uuid;
    private String nombre;
    private String slug;
    private String correo;
    private String telefono;
    private String direccion;
    private List<ServiceResponse> servicios;
    private List<UserResponse> empleados;
}

