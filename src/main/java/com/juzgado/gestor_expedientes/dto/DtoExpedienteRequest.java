package com.juzgado.gestor_expedientes.dto;

import java.time.LocalDate;

public record DtoExpedienteRequest(
        String numero,
        String descripcion,
        LocalDate fecha,
        String ubicacionFisica,
        String bodega,
        String observaciones // Nuevo campo
) {}
