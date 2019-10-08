package com.PGJ.SGV.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IUsuarioService;
@Controller
public class DashboardController {
	@Autowired
	private IUsuarioService usuarioService;
	
	@RequestMapping(value="/Dashboard", method = RequestMethod.GET)
	public String listar(Model model, Authentication authentication) {
		String usuario = authentication.getName();
		Usuario usus = new Usuario();
		usus = usuarioService.findbyAdscripcion(usuario);
		
		model.addAttribute("Usuario",usus.getNombre()+" "+usus.getApellido1());				
		return "Dashboard";
	}
	
	
}
