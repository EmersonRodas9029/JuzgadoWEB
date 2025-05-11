package com.juzgado.gestor_expedientes.dto;

import java.time.LocalDate;

public record DtoExpedienteResponse(
        Long id,
        String numero,
        String descripcion,
        LocalDate fecha,
        String ubicacionFisica,
        String creadoPor
) {}
