package com.juzgado.gestor_expedientes.service;

import com.juzgado.gestor_expedientes.dto.DtoExpedienteRequest;
import com.juzgado.gestor_expedientes.dto.DtoExpedienteResponse;
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

    public List<DtoExpedienteResponse> listarPorRol(Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElseThrow();

        List<Expediente> expedientes;

        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            expedientes = expedienteRepository.findAll();
        } else {
            expedientes = expedienteRepository.findByUsuarioCreador(usuario)
                    .stream()
                    .sorted((e1, e2) -> e2.getFecha().compareTo(e1.getFecha()))
                    .limit(5)
                    .collect(Collectors.toList());
        }

        return expedientes.stream()
                .map(e -> new DtoExpedienteResponse(
                        e.getId(),
                        e.getNumero(),
                        e.getDescripcion(),
                        e.getFecha(),
                        e.getUbicacionFisica(),
                        e.getUsuarioCreador() != null ? e.getUsuarioCreador().getUsername() : "N/A",
                        e.getBodega(),
                        e.getObservaciones()
                ))
                .collect(Collectors.toList());
    }

    public List<DtoExpedienteResponse> buscarPorNumero(String numero, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElseThrow();

        List<Expediente> expedientesFiltrados;

        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            expedientesFiltrados = expedienteRepository.findByNumeroStartingWithIgnoreCase(numero);
        } else {
            expedientesFiltrados = expedienteRepository.findByNumeroStartingWithIgnoreCase(numero).stream()
                    .filter(e -> e.getUsuarioCreador() != null && e.getUsuarioCreador().getId().equals(usuario.getId()))
                    .collect(Collectors.toList());
        }

        return expedientesFiltrados.stream()
                .map(e -> new DtoExpedienteResponse(
                        e.getId(),
                        e.getNumero(),
                        e.getDescripcion(),
                        e.getFecha(),
                        e.getUbicacionFisica(),
                        e.getUsuarioCreador() != null ? e.getUsuarioCreador().getUsername() : "N/A",
                        e.getBodega(),
                        e.getObservaciones()
                ))
                .collect(Collectors.toList());
    }

    public String crear(DtoExpedienteRequest dto, Principal principal) {
        List<Expediente> existentes = expedienteRepository.findByNumeroStartingWithIgnoreCase(dto.numero());
        if (!existentes.isEmpty()) {
            return "Ya existe un expediente con ese número.";
        }

        Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElse(null);
        Expediente e = new Expediente();
        e.setNumero(dto.numero());
        e.setDescripcion(dto.descripcion());
        e.setFecha(dto.fecha());
        e.setUbicacionFisica(dto.ubicacionFisica());
        e.setBodega(dto.bodega());
        e.setObservaciones(dto.observaciones()); // Nuevo campo
        e.setUsuarioCreador(usuario);
        expedienteRepository.save(e);

        return null;
    }

    public DtoExpedienteRequest obtenerPorId(Long id) {
        Expediente e = expedienteRepository.findById(id).orElseThrow();
        return new DtoExpedienteRequest(
                e.getNumero(),
                e.getDescripcion(),
                e.getFecha(),
                e.getUbicacionFisica(),
                e.getBodega(),
                e.getObservaciones() // Nuevo campo
        );
    }

    public void actualizar(Long id, DtoExpedienteRequest dto) {
        Expediente e = expedienteRepository.findById(id).orElseThrow();
        e.setNumero(dto.numero());
        e.setDescripcion(dto.descripcion());
        e.setFecha(dto.fecha());
        e.setUbicacionFisica(dto.ubicacionFisica());
        e.setBodega(dto.bodega());
        e.setObservaciones(dto.observaciones()); // Nuevo campo
        expedienteRepository.save(e);
    }

    public void eliminar(Long id) {
        expedienteRepository.deleteById(id);
    }
}
