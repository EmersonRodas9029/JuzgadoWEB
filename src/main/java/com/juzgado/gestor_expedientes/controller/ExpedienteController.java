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

    @GetMapping
    public String listar(@RequestParam(value = "numero", required = false) String numero, Model model, Principal principal) {
        List<DtoExpedienteResponse> expedientes = (numero != null && !numero.isEmpty())
                ? expedienteService.buscarPorNumero(numero, principal)
                : expedienteService.listarPorRol(principal);

        model.addAttribute("expedientes", expedientes);
        return "expedientes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioCrear(Model model) {
        model.addAttribute("expediente", new DtoExpedienteRequest("", "", LocalDate.now(), "", "", ""));
        return "expedientes/formulario";
    }

    @PostMapping
    public String guardar(@ModelAttribute("expediente") DtoExpedienteRequest dto, Principal principal, Model model) {
        String error = expedienteService.crear(dto, principal);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("expediente", dto);
            return "expedientes/formulario";
        }
        return "redirect:/expedientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("expediente", expedienteService.obtenerPorId(id));
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
