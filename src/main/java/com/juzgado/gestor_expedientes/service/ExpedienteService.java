package com.juzgado.gestor_expedientes.service;

import com.juzgado.gestor_expedientes.dto.*;
import com.juzgado.gestor_expedientes.entity.*;
import com.juzgado.gestor_expedientes.repository.*;
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
                        e.getId(), e.getNumero(), e.getDescripcion(),
                        e.getFecha(), e.getUbicacionFisica(),
                        e.getUsuarioCreador() != null ? e.getUsuarioCreador().getUsername() : "N/A"
                )).collect(Collectors.toList());
    }

    public void crear(DtoExpedienteRequest dto, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElse(null);
        Expediente e = new Expediente();
        e.setNumero(dto.getNumero());
        e.setDescripcion(dto.getDescripcion());
        e.setFecha(dto.getFecha());
        e.setUbicacionFisica(dto.getUbicacionFisica());
        e.setUsuarioCreador(usuario);
        expedienteRepository.save(e);
    }

    public DtoExpedienteRequest obtenerPorId(Long id) {
        Expediente e = expedienteRepository.findById(id).orElseThrow();
        return new DtoExpedienteRequest(e.getNumero(), e.getDescripcion(), e.getFecha(), e.getUbicacionFisica());
    }

    public void actualizar(Long id, DtoExpedienteRequest dto) {
        Expediente e = expedienteRepository.findById(id).orElseThrow();
        e.setNumero(dto.getNumero());
        e.setDescripcion(dto.getDescripcion());
        e.setFecha(dto.getFecha());
        e.setUbicacionFisica(dto.getUbicacionFisica());
        expedienteRepository.save(e);
    }

    public void eliminar(Long id) {
        expedienteRepository.deleteById(id);
    }
}
