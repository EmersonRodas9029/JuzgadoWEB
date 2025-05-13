package com.juzgado.gestor_expedientes.controller;

import com.juzgado.gestor_expedientes.dto.DtoRegistroUsuario;
import com.juzgado.gestor_expedientes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new DtoRegistroUsuario("", "", "USUARIO"));
        }
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") DtoRegistroUsuario dto,
                                   RedirectAttributes redirectAttributes) {
        if (usuarioService.existePorUsername(dto.username())) {
            redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya está en uso.");
            redirectAttributes.addFlashAttribute("usuario", dto);
            return "redirect:/registro";
        }

        usuarioService.registrar(dto);
        redirectAttributes.addFlashAttribute("success", "Usuario registrado correctamente.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
