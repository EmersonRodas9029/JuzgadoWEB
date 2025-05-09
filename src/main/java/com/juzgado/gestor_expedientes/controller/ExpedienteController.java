package com.juzgado.gestor_expedientes.controller;

import com.juzgado.gestor_expedientes.dto.DtoExpedienteRequest;
import com.juzgado.gestor_expedientes.service.ExpedienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/expedientes")
public class ExpedienteController {

    @Autowired
    private ExpedienteService expedienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("expedientes", expedienteService.listarTodos());
        return "expedientes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioCrear(Model model) {
        model.addAttribute("expediente", new DtoExpedienteRequest());
        return "expedientes/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("expediente") DtoExpedienteRequest dto, Principal principal) {
        expedienteService.crear(dto, principal);
        return "redirect:/expedientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("expediente", expedienteService.obtenerPorId(id));
        model.addAttribute("id", id);
        return "expedientes/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("expediente") DtoExpedienteRequest dto) {
        expedienteService.actualizar(id, dto);
        return "redirect:/expedientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        expedienteService.eliminar(id);
        return "redirect:/expedientes";
    }
}
