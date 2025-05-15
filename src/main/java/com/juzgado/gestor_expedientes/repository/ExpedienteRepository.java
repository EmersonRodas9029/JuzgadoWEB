package com.juzgado.gestor_expedientes.repository;

import com.juzgado.gestor_expedientes.entity.Expediente;
import com.juzgado.gestor_expedientes.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
    // Buscar expedientes que comienzan con un número (ignorando mayúsculas)
    List<Expediente> findByNumeroStartingWithIgnoreCase(String numero);

    // Buscar expedientes creados por un usuario específico
    List<Expediente> findByUsuarioCreador(Usuario usuario);
}
