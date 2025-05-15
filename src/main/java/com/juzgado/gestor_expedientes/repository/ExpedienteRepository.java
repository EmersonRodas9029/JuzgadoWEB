package com.juzgado.gestor_expedientes.repository;

import com.juzgado.gestor_expedientes.entity.Expediente;
import com.juzgado.gestor_expedientes.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
    List<Expediente> findByNumeroStartingWithIgnoreCase(String numero);

    List<Expediente> findByUsuarioCreador(Usuario usuario);

    // Nuevo método: expedientes con mismo número por usuario
    List<Expediente> findByNumeroAndUsuarioCreadorUsernameIgnoreCase(String numero, String username);
}
