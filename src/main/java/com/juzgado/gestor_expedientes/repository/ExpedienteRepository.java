package com.juzgado.gestor_expedientes.repository;

import com.juzgado.gestor_expedientes.entity.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
    Optional<Expediente> findByNumero(String numero);
}