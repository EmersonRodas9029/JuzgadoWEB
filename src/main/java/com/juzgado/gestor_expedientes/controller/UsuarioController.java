package com.juzgado.gestor_expedientes.controller;

import com.juzgado.gestor_expedientes.dto.DtoRegistroUsuario;
import com.juzgado.gestor_expedientes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String formularioRegistro(@ModelAttribute("usuario") DtoRegistroUsuario dto) {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") DtoRegistroUsuario dto) {
        usuarioService.registrar(dto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
