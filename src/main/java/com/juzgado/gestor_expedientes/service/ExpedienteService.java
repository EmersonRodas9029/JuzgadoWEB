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

    // Método para listar todos los expedientes
    public List<DtoExpedienteResponse> listarTodos() {
        return expedienteRepository.findAll().stream()
                .map(e -> new DtoExpedienteResponse(
                        e.getId(),
                        e.getNumero(),
                        e.getDescripcion(),
                        e.getFecha(),
                        e.getUbicacionFisica(),
                        e.getBodega(),
                        e.getUsuarioCreador() != null ? e.getUsuarioCreador().getUsername() : "N/A"
                ))
                .collect(Collectors.toList());
    }

    // Método para buscar expedientes por número
    public List<DtoExpedienteResponse> buscarPorNumero(String numero) {
        return expedienteRepository.findByNumero(numero).stream()
                .map(e -> new DtoExpedienteResponse(
                        e.getId(),
                        e.getNumero(),
                        e.getDescripcion(),
                        e.getFecha(),
                        e.getUbicacionFisica(),
                        e.getBodega(),
                        e.getUsuarioCreador() != null ? e.getUsuarioCreador().getUsername() : "N/A"
                ))
                .collect(Collectors.toList());
    }

    // Método para crear un nuevo expediente
    public void crear(DtoExpedienteRequest dto, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElse(null);
        Expediente e = new Expediente();
        e.setNumero(dto.numero());
        e.setDescripcion(dto.descripcion());
        e.setFecha(dto.fecha());
        e.setUbicacionFisica(dto.ubicacionFisica());
        e.setBodega(dto.bodega());
        e.setUsuarioCreador(usuario);
        expedienteRepository.save(e);
    }

    // Método para obtener un expediente por su ID
    public DtoExpedienteRequest obtenerPorId(Long id) {
        Expediente e = expedienteRepository.findById(id).orElseThrow();
        return new DtoExpedienteRequest(
                e.getNumero(),
                e.getDescripcion(),
                e.getFecha(),
                e.getUbicacionFisica(),
                e.getBodega()
        );
    }

    // Método para actualizar un expediente
    public void actualizar(Long id, DtoExpedienteRequest dto) {
        Expediente e = expedienteRepository.findById(id).orElseThrow();
        e.setNumero(dto.numero());
        e.setDescripcion(dto.descripcion());
        e.setFecha(dto.fecha());
        e.setUbicacionFisica(dto.ubicacionFisica());
        e.setBodega(dto.bodega());
        expedienteRepository.save(e);
    }

    // Método para eliminar un expediente
    public void eliminar(Long id) {
        expedienteRepository.deleteById(id);
    }
}
