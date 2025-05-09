package com.juzgado.gestor_expedientes.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DtoUsuarioResponse {
    private Long id;
    private String username;
    private String rol;
}
