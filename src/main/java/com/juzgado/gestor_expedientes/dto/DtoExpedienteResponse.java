package com.juzgado.gestor_expedientes.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DtoExpedienteResponse {
    private Long id;
    private String numero;
    private String descripcion;
    private LocalDate fecha;
    private String ubicacionFisica;
    private String creadoPor;
}
