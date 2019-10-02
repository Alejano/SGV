package com.PGJ.SGV.controllers;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Authority;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IAutoridadService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.util.paginador.PageRender;


@Controller
public class UsuarioController {
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IAdscripcionService adscripService;
	@Autowired
	private IAutoridadService autoridadService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	List<Adscripcion> adscripcion = new ArrayList<Adscripcion>();
	List<Usuario> usuarios = new ArrayList<Usuario>();
	List<Authority> autoridad = new ArrayList<Authority>();
	String empleado ="";
	static boolean editar = false;
	
	@RequestMapping(value="/Usuarios", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model) {			
		
		
		Pageable pageRequest = PageRequest.of(page, 100);
		
		
		Page<Usuario> usuarioPage = usuarioService.findAll(pageRequest);
		PageRender <Usuario> usuarioRender = new PageRender<>("/Usuarios",usuarioPage);
		if(usuarioService.totalUsuarios()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
	
		model.addAttribute("usuarios",usuarioPage);
		model.addAttribute("page",usuarioRender);
		
		//model.addAttribute("autoridad",autoridad);
		//model.addAttribute("adslist",adscripcion);
		model.addAttribute("titulo","Listado de Usuarios");
		//model.addAttribute("usuarios",usuarios);
		
		return "Usuarios";
	}
		
	
	@RequestMapping(value="/formUsu")
	public String crear(Map<String,Object> model) {
		usuarios = usuarioService.findAll();
		autoridad = autoridadService.findAll();
		adscripcion = adscripService.findAll();
		Usuario usuario = new Usuario();			
				
		model.put("adslist", adscripcion);
		model.put("usuarios", usuario);
		model.put("titulo", "Formulario de Adscripciones");
							
		return "formUsu";
	}
	
	@RequestMapping(value="/formUsu/{no_empleado}")
	public String editar(@PathVariable(value="no_empleado") String no_empleado,Map<String,Object>model) {
		adscripcion = adscripService.findAll();
		editar = true;
		Usuario usuario = null;
		
		if(!no_empleado.equals(null)) {
			usuario = usuarioService.findOne(no_empleado);
			empleado = usuario.getNo_empleado();
		}else {
			return "redirect:/Usuarios";
		}
		
		model.put("adslist", adscripcion);
		model.put("usuarios",usuario);
		model.put("titulo", "Editar cliente");
		return "formUsu";
	}
	
	
	@RequestMapping(value="/formUsu",method = RequestMethod.POST)
	public String guardar(Usuario usuario,Model model){		
		Adscripcion adc = new Adscripcion();
				
		var password = usuario.getContrasena();		
		String bcryptPassword = passwordEncoder.encode(password);		
		usuario.setContrasena(bcryptPassword);	
		
		
		if(editar==false) {
			for(Usuario usu:usuarios) {
				if(usuario.getNo_empleado().equals(usu.getNo_empleado())) {					
					return "redirect:/idDuplicadoUsu/"+usuario.getNo_empleado();
				};	
			}
		}else{
																
				if( !empleado.equals(usuario.getNo_empleado())) {		
					//System.out.println(usuario.getNo_empleado()+" "+ empleado);
					return "redirect:/idDuplicadoUsuCrea/"+usuario.getNo_empleado()+"/"+empleado;
				}									
			
		};
					
				
		for(Adscripcion ads:adscripcion) {
			if(ads.getId_adscripcion()==usuario.getAdscripcion().getId_adscripcion()) {
				adc = ads;
			};
		}
					
		usuario.setAdscripcion(adc);				
		usuarioService.save(usuario);			
		editar = false;						
		return "redirect:Usuarios";
	}	
	
	@RequestMapping(value="/estadoUsu/{no_empleado}/{enabled}")
	public String estado (@PathVariable(value="no_empleado")String no_empleado,@PathVariable(value="enabled")boolean enabled) {
		Usuario uss = new Usuario();
		boolean seteo = false;
		
		uss = usuarioService.findOne(no_empleado);
		if(enabled) {
			seteo=false;
			uss.setEnabled(seteo);
		}else {
			seteo=true;
			uss.setEnabled(seteo);	
		}
		
					
		
		usuarioService.save(uss);		
		
	return "redirect:/Usuarios";
	}
	
	@RequestMapping(value="/elimUsu/{no_empleado}")
	public String eliminar (@PathVariable(value="no_empleado")String no_empleado) {
	
		
		if(no_empleado != "") {								
					usuarioService.delete(no_empleado);
			
		}
		
		return "redirect:/Usuarios";
	}
	
	@RequestMapping(value="/formUsuBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(value="elemento") String elemento,Model model, Authentication authentication){	
		Pageable pageRequest = PageRequest.of(page, 100);
		 Double Dato;
		 
		 if(!elemento.isBlank()) {			
				if(isValidDouble(elemento)) {
						Dato = Double.parseDouble(elemento);
						DecimalFormat formt = new DecimalFormat("0");
						elemento = formt.format(Dato);
						elemento = elemento.replaceAll(",","");	
				};
		 
		 Page<Usuario> usuariospage = usuarioService.finUsuElemntPage(elemento, pageRequest);
		 PageRender<Usuario> renderpage = new PageRender<>("/formUsuBuscar",usuariospage);
		 
		 model.addAttribute("usuarios",usuariospage);
		 model.addAttribute("page",renderpage);
		 model.addAttribute("elemento",elemento);
		 return "Usuarios";
		 }
		 
		return "redirect:/Usuarios";
	}
		 
		 private static boolean isValidDouble(String s) {
				final String Digits     = "(\\p{Digit}+)";
				  final String HexDigits  = "(\\p{XDigit}+)";
				
				  final String Exp        = "[eE][+-]?"+Digits;
				  final String fpRegex    =
				      ("[\\x00-\\x20]*"+  
				       "[+-]?(" + // Optional sign character		       		           
				       // Digitos ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
				       "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

				       // . Digitos ExponentPart_opt FloatTypeSuffix_opt
				       "(\\.("+Digits+")("+Exp+")?)|"+

				       // Hexadecimal strings
				       "((" +
				        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
				        "(0[xX]" + HexDigits + "(\\.)?)|" +

				        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
				        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

				        ")[pP][+-]?" + Digits + "))" +
				       "[fFdD]?))" +
				       "[\\x00-\\x20]*");

				  return Pattern.matches(fpRegex, s);
			}
	
	
}
