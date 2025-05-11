package com.juzgado.gestor_expedientes.repository;

import com.juzgado.gestor_expedientes.entity.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
    // Método para encontrar expedientes por número
    List<Expediente> findByNumero(String numero);
}
