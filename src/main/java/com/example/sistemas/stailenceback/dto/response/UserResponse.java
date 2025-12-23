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
public class UserResponse {
    private Long id;
    private String uuid;
    private String correo;
    private String telefono;
    private String nombres;
    private String apellidos;
    private String avatarUrl;
}

