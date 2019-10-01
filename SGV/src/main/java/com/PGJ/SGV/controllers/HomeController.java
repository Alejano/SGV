package com.PGJ.SGV.controllers;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IUsuarioService;

@Controller
public class HomeController {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	Usuario usuario=new Usuario();	
	@Autowired
	private IUsuarioService usuarioService;

	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String HomeBarra(Model model, Authentication authentication,HttpServletRequest request) {
		var nombre="";
		var user="";
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";						
			model.addAttribute("usuario",user);
		}else {
			if(hasRole("ROLE_USER")) {
				user = "ROLE_USER";
				model.addAttribute("usuario",user);				
			}
		};	    
		usuario = usuarioService.findOne(authentication.getName());
		nombre= usuario.getNombre();
	   model.addAttribute("id",authentication.getPrincipal());
	   model.addAttribute("Online",nombre); 		   	
	   
	   
		return "home";
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String Home(Model model, Authentication authentication) {
		var nombre="";
		var UAuth ="";
		if(hasRole("ROLE_ADMIN")) {
			UAuth = "ROLE_ADMIN";
			model.addAttribute("usuario",UAuth);
		}else {
			if(hasRole("ROLE_USER")) {
				UAuth = "ROLE_USER";
				model.addAttribute("usuario",UAuth);
			}
		}	    		    	  
						   		
		
		usuario = usuarioService.findOne(authentication.getName());    
		nombre= usuario.getNombre();
		model.addAttribute("id",authentication.getName());
		 model.addAttribute("Online",nombre); 		
		return "home";
	}
	
	
	public static boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context==null) {
		return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> autorithies = auth.getAuthorities();
		for(GrantedAuthority authority: autorithies) {
			if(role.equals(authority.getAuthority())) {return true;}
		}
		return false;
	}

/*
	
	@GetMapping({"/home","/"})	
	public String login() {
		
		
				
		return "home";
	}
*/
}
