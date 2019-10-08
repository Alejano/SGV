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

import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.ISeguroService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;


@Controller
public class VehiculoController {
	List<Adscripcion> adscripcionlist = new ArrayList<Adscripcion>();
	List<Seguro> seguros = new ArrayList<Seguro>();
	List<Vehiculo> vehiculo = new ArrayList<Vehiculo>();
	boolean editar = false;
	String coche="";
	
	@Autowired
	private IVehiculoService vehiculoService;	
	@Autowired
	private IAdscripcionService adscripService;
	@Autowired
	private ISeguroService seguroService;
	@Autowired
	private IUsuarioService usuarioService;

	
	@RequestMapping(value="/Vehiculos", method = RequestMethod.GET)
	public String listar(Model model,Authentication authentication,@RequestParam(name="page", defaultValue = "0") int page) {		
		//List<Vehiculo> vehiculoArea = new ArrayList<Vehiculo>();		
		var ads="";						
		ads = authentication.getName();
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
		Pageable pageRequest = PageRequest.of(page, 100);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);								
			
			//vehiculoArea = vehiculoService.findVehiculosArea(usus.getAdscripcion().getId_adscripcion());
			Page<Vehiculo> vehiculoPageAra = vehiculoService.findVehiculosAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculoPageAra);
			if(vehiculoService.totalVehiculo()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			//model.addAttribute("vehiculos",vehiculoArea);
			model.addAttribute("vehiculos",vehiculoPageAra);
			model.addAttribute("page",pageRender);
			
			return "Vehiculos";
		}
		
		adscripcionlist = adscripService.findAll();
		seguros = seguroService.findAll();
		vehiculo = vehiculoService.findAll();		
		
		Page<Vehiculo> vehiculopage = vehiculoService.findAllPage(pageRequest);
		PageRender<Vehiculo> pageRender = new PageRender<>("/Vehiculos", vehiculopage);
		int tamaño = 7;
		if(vehiculoService.totalVehiculo()>= tamaño) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		
		model.addAttribute("titulo","Listado de Vehiculos");
		//model.addAttribute("vehiculos",vehiculo);
		model.addAttribute("vehiculos",vehiculopage);
		model.addAttribute("page",pageRender);
		
		
		return "Vehiculos";
	}
		
	
	@RequestMapping(value="/formVehi")
	public String crear(Authentication authentication,Map<String,Object> model) {
		
		var ads="";						
		ads = authentication.getName();
		var user="";
		
		
		if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";model.put("usuario",user);}else {if(hasRole("ROLE_USER")) {user = "ROLE_USER";model.put("usuario",user);};};
		if(user.equals("ROLE_USER")) {
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			
			Vehiculo vehi = new Vehiculo();
			vehi.setAdscripcion(usus.getAdscripcion());
			
			List<Seguro> segu = new ArrayList<Seguro>();
			segu = seguroService.FindSeguroArea(usus.getAdscripcion().getId_adscripcion());
			model.put("seguroslist", segu);
			model.put("nombreAds",usus.getAdscripcion().getNombre_adscripcion() );
			model.put("adscripcion",vehi.getAdscripcion());
			model.put("vehiculo", vehi);
			model.put("titulo", "Formulario de Vehiculos");
								
			return "formVehi";
		};
		
		adscripcionlist = adscripService.findAll();
		seguros = seguroService.findAll();
	
		Vehiculo vehi = new Vehiculo();
		model.put("seguroslist", seguros);
		model.put("adslist",adscripcionlist );
		model.put("vehiculo", vehi);
		model.put("titulo", "Formulario de Vehiculos");
							
		return "formVehi";
	}
	
	@RequestMapping(value="/formVehi/{placa}")
	public String editar(@PathVariable(value="placa") String placa,Map<String,Object>model) {		
		Vehiculo vehiculo = null;
		
		var user="";			
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";						
			model.put("usuario",user);
		}else {
			if(hasRole("ROLE_USER")) {
				user = "ROLE_USER";
				model.put("usuario",user);				
			}
		}
		
		editar = true;
		if(!placa.equals("")) {
			vehiculo = vehiculoService.findOne(placa);	
			coche = vehiculo.getPlaca();
		}else {
			return "redirect:/Adscripcion";
		}
		model.put("seguroslist", seguros);
		model.put("adslist",adscripcionlist );		
		model.put("vehiculo",vehiculo);
		model.put("titulo", "Editar cliente");
		return "formVehi";
	}
	
	
	@RequestMapping(value="/formVehi",method = RequestMethod.POST)
	public String guardar(Authentication authentication,Vehiculo vehiculox){
		String fecha = vehiculox.getAno().toString();
		vehiculox.setAno(fecha);
	
		if(editar == false) {
			for(Vehiculo v:vehiculo) {
				if(v.getPlaca().equals(vehiculox.getPlaca())) {
					return "redirect:/idDuplicadoVehi/"+vehiculox.getPlaca();
				};
			}
		}else{
				if(!coche.equals(vehiculox.getPlaca())) {
						return "redirect:/idDuplicadoVehiCrea/"+vehiculox.getPlaca()+"/"+coche;
				};
		};
			if(hasRole("ROLE_USER")) {
						String ad = authentication.getName();
						Usuario A =new Usuario();
						A= usuarioService.findbyAdscripcion(ad);
						vehiculox.setAdscripcion(A.getAdscripcion());
						vehiculoService.save(vehiculox);			
						editar = false;						
						return "redirect:Vehiculos";
			}
		
				adscripcionlist = adscripService.findAll();
					for(Adscripcion ads:adscripcionlist) {
				if(vehiculox.getAdscripcion().getId_adscripcion()==ads.getId_adscripcion()) {
					vehiculox.setAdscripcion(ads);
				};
			}
		
		vehiculoService.save(vehiculox);			
		editar = false;						
		return "redirect:Vehiculos";
	}
	
	@RequestMapping(value="/elimVehi/{placa}")
	public String eliminar (@PathVariable(value="placa")String placa) {
		
		if(placa != "") {
			vehiculoService.delete(placa);
		}
		return "redirect:/Vehiculos";
	}
	
	@RequestMapping(value="/estadoVehi/{placa}/{estado}")
	public String estado (@PathVariable(value="placa")String placa,@PathVariable(value="estado")String estado) {
		Vehiculo v = new Vehiculo();
		var seteo = "";
		if(placa != "") {
			v = vehiculoService.findOne(placa);
			switch (estado) {
			case "DISPONIBLE":
								seteo="NO DISPONIBLE";
								v.setEstado(seteo);
				break;

			case "NO DISPONIBLE":
								seteo="DISPONIBLE";
								v.setEstado(seteo);				
				break;
				
			
					};
		}else {
		return "redirect:/Vehiculos";
		}
		vehiculoService.save(v);
		System.out.println(v.getEstado());
		
	return "redirect:/Vehiculos";
	}
	
	@RequestMapping(value="/formVehBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") 
	int page,@RequestParam(value="elemento") String elemento,Model model , Authentication authentication){						 
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
						Page<Vehiculo> vehiculopage = vehiculoService.findVehElemAreaPage(usus.getAdscripcion().getId_adscripcion(), elemento, pageRequest);
					PageRender<Vehiculo> pageRender = new PageRender<>("/formVehBuscar?elemento="+elemento, vehiculopage);
					model.addAttribute("vehiculos",vehiculopage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elemento);
					return "Vehiculos";
			};					
			  Page<Vehiculo> vehiculopage= vehiculoService.findVehElemntoPage("%"+elemento+"%", pageRequest);		 									
			PageRender<Vehiculo> pageRender = new PageRender<>("/formVehBuscar?elemento="+elemento, vehiculopage);
			model.addAttribute("vehiculos",vehiculopage);
			model.addAttribute("page",pageRender);
			model.addAttribute("elemento",elemento);	
			return "Vehiculos";
		}else {
			return "redirect:/Vehiculos";
		}
						
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
