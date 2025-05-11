package com.juzgado.gestor_expedientes.controller;

import com.juzgado.gestor_expedientes.dto.DtoExpedienteRequest;
import com.juzgado.gestor_expedientes.dto.DtoExpedienteResponse;
import com.juzgado.gestor_expedientes.service.ExpedienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/expedientes")
public class ExpedienteController {

    @Autowired
    private ExpedienteService expedienteService;

    // Método para listar todos los expedientes o buscar por número
    @GetMapping
    public String listar(@RequestParam(value = "numero", required = false) String numero, Model model) {
        List<DtoExpedienteResponse> expedientes;
        if (numero != null && !numero.isEmpty()) {
            expedientes = expedienteService.buscarPorNumero(numero);
        } else {
            expedientes = expedienteService.listarTodos();
        }
        model.addAttribute("expedientes", expedientes);
        return "expedientes/lista";
    }

    // Método para mostrar el formulario de creación de un expediente
    @GetMapping("/nuevo")
    public String formularioCrear(Model model) {
        DtoExpedienteRequest expedienteVacio = new DtoExpedienteRequest("", "", LocalDate.now(), "");
        model.addAttribute("expediente", expedienteVacio);
        return "expedientes/formulario";
    }

    // Método para guardar un expediente
    @PostMapping
    public String guardar(@ModelAttribute("expediente") DtoExpedienteRequest dto, Principal principal) {
        expedienteService.crear(dto, principal);
        return "redirect:/expedientes";
    }

    // Método para mostrar el formulario de edición de un expediente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("expediente", expedienteService.obtenerPorId(id));
        return "expedientes/formulario";
    }

    // Método para actualizar un expediente
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("expediente") DtoExpedienteRequest dto) {
        expedienteService.actualizar(id, dto);
        return "redirect:/expedientes";
    }

    // Método para eliminar un expediente
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        expedienteService.eliminar(id);
        return "redirect:/expedientes";
    }
}
