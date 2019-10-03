package com.PGJ.SGV.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PGJ.SGV.models.entity.Mantenimiento;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IMantenimientoService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;


@Controller
public class MantenimintoController {
	List<Mantenimiento> mantenimiento = new ArrayList<Mantenimiento>();
	List<Vehiculo> vehiculo = new ArrayList<Vehiculo>();
	static boolean Editar = false;
	@Autowired
	private IMantenimientoService mantService;

	@Autowired
	private IVehiculoService vehiculoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	

	@RequestMapping(value="/Mantenimientos", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model, Model model2, Authentication authentication) {				
		
		//List<Mantenimiento> MantArea = new ArrayList<Mantenimiento>();
		
		var ads="";		
		var user="";
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";						
			model.addAttribute("usuario",user);
		}else {
			if(hasRole("ROLE_USER")) {
				user = "ROLE_USER";
				model.addAttribute("usuario",user);				
			}
		}
		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
		
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);	
			
			//MantArea = mantService.FindMantenimientoArea(usus.getAdscripcion().getId_adscripcion());
			Page<Mantenimiento> MantAreaPage = mantService.FindMantenimientoAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Mantenimiento> MantRenderArea= new PageRender<>("/Mantenimientos",MantAreaPage);
			if(mantService.totalMantenimiento()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			model.addAttribute("mantenimientos",MantAreaPage);
			model.addAttribute("page",MantRenderArea);
			return "Mantenimientos";
		}
		
		//mantenimiento = mantService.findAll();
		
		Page<Mantenimiento> MantPage = mantService.findAll(pageRequest);
		PageRender<Mantenimiento> MantRender= new PageRender<>("/Mantenimientos",MantPage);
		if(mantService.totalMantenimiento()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};		
		model.addAttribute("titulo","Listado de Mantenimientos");
		model.addAttribute("mantenimientos",MantPage);
		model.addAttribute("page",MantRender);
		
		return "Mantenimientos";
	}
	
	
	@RequestMapping(value="/Mantenimientos/{placa}", method = RequestMethod.GET)
	public String listarPlaca(@RequestParam(name="page", defaultValue = "0") int page,@PathVariable(value="placa") String placa,Model model, Authentication authentication) {				
		
		//List<Mantenimiento> MantArea = new ArrayList<Mantenimiento>();
		
		var ads="";		
		var user="";
					if(hasRole("ROLE_ADMIN")) {
						user ="ROLE_ADMIN";						
						model.addAttribute("usuario",user);
					}else {
						if(hasRole("ROLE_USER")) {
							user = "ROLE_USER";
							model.addAttribute("usuario",user);				
						}
					}
					
		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
			if(user.equals("ROLE_USER")){
				Usuario usus = new Usuario();
				usus = usuarioService.findbyAdscripcion(ads);	
				
				//MantArea = mantService.FindMantPlacaAds(placa, usus.getAdscripcion().getId_adscripcion());
				Page<Mantenimiento> mantAreaPage = mantService.FindMantPlacaAreaPage(placa, usus.getAdscripcion().getId_adscripcion(), pageRequest);
				PageRender<Mantenimiento> mantRender = new PageRender<>("/Mantenimientos/{placa}",mantAreaPage);
				
				model.addAttribute("placa",placa);							
				model.addAttribute("mantenimientos",mantAreaPage);
				model.addAttribute("page",mantRender);
				return "Mantenimientos";
			}
			
		//mantenimiento = mantService.FindMantPlaca(placa);
			Page<Mantenimiento> mantPage = mantService.FindMantPlacaPage(placa, pageRequest);
			PageRender<Mantenimiento> mantRender = new PageRender<>("/Mantenimientos/"+placa,mantPage);
				
		model.addAttribute("titulo","Listado de Mantenimientos");
		model.addAttribute("placa",placa);
		model.addAttribute("mantenimientos",mantPage);
		model.addAttribute("page",mantRender);
		return "Mantenimientos";
	}
		
	
	@RequestMapping(value="/VehiMant/{placa}")
	public String crear(@PathVariable(value="placa") String placa,Map<String,Object> model) {	
			
		long lugar=0;
		if(!placa.equals(null)) {
			
				Double kilometraje= vehiculoService.kilometraje(placa);
				lugar = mantService.ultimoRegistroMant();
				Mantenimiento mantenimiento = new Mantenimiento();
				mantenimiento.setId_mantenimiento(lugar+1);
				mantenimiento.setVehiculo(vehiculoService.findOne(placa));
				mantenimiento.setKilometraje(kilometraje);
		model.put("mantenimiento",mantenimiento );
		model.put("placa",placa);
		//model.put("kilometraje",kilometraje);	
		model.put("titulo", "Formulario de Mantenimiento");					
		return "formMant";
		}
		return "Mantenimientos/"+placa;
	}
	
	@RequestMapping(value="/formMant/{id_mantenimiento}")
	public String editar(@PathVariable(value="id_mantenimiento") Long id_mantenimiento,Map<String,Object>model) {	
		Editar = true;			
		Mantenimiento mant = null;
		
		if(id_mantenimiento != null) {
			mant = mantService.findOne(id_mantenimiento);
		}else {
			return "redirect:/Mantenimientos";
		}
		model.put("vehiculo", vehiculo);
		model.put("placa", mant.getVehiculo().getPlaca());
		model.put("mantenimiento",mant);
		model.put("titulo", "Editar Mantenimiento");
		return "formMant";
	}
	
	
	@RequestMapping(value="/formMant",method = RequestMethod.POST)
	public String guardar(Mantenimiento mantenimiento){			
			Vehiculo vehiculoselect =new Vehiculo();										
		
			if(Editar == true) {
				
			
				vehiculoselect = vehiculoService.findOne(mantenimiento.getVehiculo().getPlaca());	
				mantenimiento.setKilometraje(vehiculoselect.getKilometraje());
				mantenimiento.setVehiculo(vehiculoselect);
				mantService.save(mantenimiento);
				Editar = false;	
				return "redirect:/Mantenimientos";
			}
			
			vehiculoselect = vehiculoService.findOne(mantenimiento.getVehiculo().getPlaca());							
			mantenimiento.setVehiculo(vehiculoselect);
			mantenimiento.setKilometraje(vehiculoselect.getKilometraje());
						
			mantService.save(mantenimiento);	
											
		return "redirect:/Mantenimientos/"+mantenimiento.getVehiculo().getPlaca();
	}
	
	@RequestMapping(value="/elimMant/{id_mantenimiento}")
	public String eliminar (@PathVariable(value="id_mantenimiento")Long id_mantenimiento) {
		
		if(id_mantenimiento != null) {
			mantService.delete(id_mantenimiento);
		}
		return "redirect:/Mantenimientos";
	}
	
	@RequestMapping(value="/formMantBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(value="elemento") String elemento,Model model, Authentication authentication){						 
		Pageable pageRequest = PageRequest.of(page, 100);
		 Double Dato;
		 var ads="";		
		 ads = authentication.getName();	
		 var user="";
		 if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
			}else {if(hasRole("ROLE_USER")) user = "ROLE_USER"; model.addAttribute("usuario",user);}
		 	 	
		 
		if(!elemento.isBlank()) {			
				if(isValidDouble(elemento)) {
						Dato = Double.parseDouble(elemento);
						DecimalFormat formt = new DecimalFormat("0");
						elemento = formt.format(Dato);
						elemento = elemento.replaceAll(",","");	
				};
			
				 if(user == "ROLE_USER") {
					 Usuario usus = new Usuario();
						usus = usuarioService.findbyAdscripcion(ads);
						
						Page<Mantenimiento> mantspage = mantService.FindMantelemntAreaPage(usus.getAdscripcion().getId_adscripcion(),"%"+elemento+"%", pageRequest);
						
						PageRender<Mantenimiento> pageRender = new PageRender<>("/formMantBuscar?elemento="+elemento, mantspage);
						model.addAttribute("mantenimientos",mantspage);
						model.addAttribute("page",pageRender);
						model.addAttribute("elemento",elemento);
						return "Mantenimientos";
				 };
			
				 Page<Mantenimiento> mantspage= mantService.FindMantElemPage("%"+elemento+"%", pageRequest);			 									 
			PageRender<Mantenimiento> pageRender = new PageRender<>("/formMantBuscar?elemento="+elemento, mantspage);
			model.addAttribute("mantenimientos",mantspage);
			model.addAttribute("page",pageRender);
			model.addAttribute("elemento",elemento);	
			return "Mantenimientos";
		}else {
			return "redirect:/Mantenimientos";
		}
				
		
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
			

}
