package com.example.sistemas.stailenceback.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleRequest {
    @NotNull
    private Long usuarioId;

    @NotNull
    private String rol; // OWNER, MANAGER, STAFF
}

