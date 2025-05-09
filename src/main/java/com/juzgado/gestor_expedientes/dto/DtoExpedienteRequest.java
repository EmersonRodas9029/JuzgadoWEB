package com.juzgado.gestor_expedientes.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DtoExpedienteRequest {
    private String numero;
    private String descripcion;
    private LocalDate fecha;
    private String ubicacionFisica;
}
