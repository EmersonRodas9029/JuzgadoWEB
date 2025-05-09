package com.juzgado.gestor_expedientes.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DtoRegistroUsuario {
    private String username;
    private String password;
    private String rol;
}
