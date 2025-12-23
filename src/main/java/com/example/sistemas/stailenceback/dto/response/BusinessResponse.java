package com.example.sistemas.stailenceback.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessResponse {
    private Long id;
    private String uuid;
    private String nombre;
    private String slug;
    private String correo;
    private String telefono;
    private String direccion;
}

