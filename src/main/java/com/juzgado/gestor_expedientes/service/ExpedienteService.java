package com.juzgado.gestor_expedientes.service;

import com.juzgado.gestor_expedientes.dto.*;
import com.juzgado.gestor_expedientes.entity.Expediente;
import com.juzgado.gestor_expedientes.entity.Usuario;
import com.juzgado.gestor_expedientes.repository.ExpedienteRepository;
import com.juzgado.gestor_expedientes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpedienteService {

    @Autowired
    private ExpedienteRepository expedienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<DtoExpedienteResponse> listarTodos() {
        return expedienteRepository.findAll().stream()
                .map(e -> new DtoExpedienteResponse(
                        e.getId(),
                        e.getNumero(),
                        e.getDescripcion(),
                        e.getFecha(),
                        e.getUbicacionFisica(),
                        e.getUsuarioCreador() != null ? e.getUsuarioCreador().getUsername() : "N/A"
                ))
                .collect(Collectors.toList());
    }

    public void crear(DtoExpedienteRequest dto, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElse(null);
        Expediente e = new Expediente();
        e.setNumero(dto.numero());
        e.setDescripcion(dto.descripcion());
        e.setFecha(dto.fecha());
        e.setUbicacionFisica(dto.ubicacionFisica());
        e.setUsuarioCreador(usuario);
        expedienteRepository.save(e);
    }

    public DtoExpedienteRequest obtenerPorId(Long id) {
        Expediente e = expedienteRepository.findById(id).orElseThrow();
        return new DtoExpedienteRequest(
                e.getNumero(),
                e.getDescripcion(),
                e.getFecha(),
                e.getUbicacionFisica()
        );
    }

    public void actualizar(Long id, DtoExpedienteRequest dto) {
        Expediente e = expedienteRepository.findById(id).orElseThrow();
        e.setNumero(dto.numero());
        e.setDescripcion(dto.descripcion());
        e.setFecha(dto.fecha());
        e.setUbicacionFisica(dto.ubicacionFisica());
        expedienteRepository.save(e);
    }

    public void eliminar(Long id) {
        expedienteRepository.deleteById(id);
    }
}
