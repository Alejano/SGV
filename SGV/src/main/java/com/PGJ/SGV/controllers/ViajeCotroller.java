package com.PGJ.SGV.controllers;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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

import com.PGJ.SGV.models.entity.Conductor;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.Viaje;
import com.PGJ.SGV.service.IConductorService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.service.IViajeService;
import com.PGJ.SGV.util.paginador.PageRender;


@Controller
public class ViajeCotroller {
	List<Conductor> conductores = new ArrayList<Conductor>();
	List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();	
	List<Viaje> viajeslist = new ArrayList<Viaje>();
	@Autowired
	private IViajeService viajeService;
	boolean editar;
	@Autowired
	private IConductorService conductorService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IUsuarioService usuarioService;

	@RequestMapping(value="/Viajes", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model,HttpServletRequest request, Authentication authentication) {	
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
			
			//viajesArea = viajeService.ViajesArea(usus.getAdscripcion().getId_adscripcion());	
			
			Page<Viaje> viajespageArea = viajeService.ViajesAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);			
			PageRender<Viaje> pageRenderArea = new PageRender<>("/Viajes", viajespageArea);	
			if(viajeService.viajestotales() >= 7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			
			model.addAttribute("viajes",viajespageArea);
			model.addAttribute("page",pageRenderArea);
			return "Viajes";
		}	
		
		
		Page<Viaje> viajespage = viajeService.findAll(pageRequest);			
		PageRender<Viaje> pageRender = new PageRender<>("/Viajes", viajespage);				
		if(viajeService.viajestotales() >= 7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		
		
		model.addAttribute("titulo","Listado de Viajes");
		model.addAttribute("viajes",viajespage);
		model.addAttribute("page",pageRender);
		
		return "Viajes";
	}
		
	
	@RequestMapping(value="/VehiViaj/{placa}")
	public String crear(@PathVariable(value="placa") String placa,Map<String,Object> model) {
		conductores = conductorService.findAll();			
		vehiculos = vehiculoService.findAll();
		
		Viaje viaje = new Viaje();	
		
		
		if(!placa.equals(null)) {
	    for(Vehiculo vh:vehiculos) {
	    	if(vh.getPlaca().equals(placa)){
	    		viaje.setVehiculo(vh);
	    		viaje.setKilometraje_inicial(vh.getKilometraje());
	    	};
	    }		
		
		}else {
			return "redirect:/Vehiculos";
		};
				
		
		model.put("conductores", conductores);
		model.put("viaje", viaje);
		model.put("titulo", "Formulario de Adscripciones");
							
		return "formViaj";
	}
	
	@RequestMapping(value="/formViajEditar/{id_viaje}")
	public String editar(@PathVariable(value="id_viaje") Long id_viaje,Map<String,Object>model) {	
		Viaje viaje = null;
		editar = true;
		if(!id_viaje.equals(null)) {
			viaje = viajeService.findOne(id_viaje);
			
		}else {
			return "redirect:/Viajes";
		}
		
		model.put("vehiculo", vehiculos);
		model.put("conductores", conductores);		
		model.put("viaje",viaje);
		model.put("titulo", "Editar cliente");
		return "formViajEditar";
	}
	
	
	@RequestMapping(value="/formViaj",method = RequestMethod.POST)
	public String guardar(Viaje viaje){		
		
		double Di=0;
		double Df = viaje.getKilometraje_final();
		double Dt=0;
		
		
		for(Vehiculo v:vehiculos) {
			if(v.getPlaca().equals(viaje.getVehiculo().getPlaca())) {
				Di = v.getKilometraje();
				viaje.setVehiculo(v);				
			};			
		}
		
		for(Conductor con:conductores) {
			if(con.getNo_empleado().equals(viaje.getConductor().getNo_empleado())) {
				viaje.setConductor(con);
			};
		}
		
		Dt = Df - Di ;
		viaje.setKilometraje_inicial(Di);
		viaje.setDistancia_recorrida(Dt);
		
		viajeService.save(viaje);			
		if(editar) {			
			editar = true;
		return "redirect:Viajes";
		}else {
			editar = true;
		return "redirect:Vehiculos";	
		}
	}
	
	@RequestMapping(value="/elimViaj/{id_viaje}")
	public String eliminar (@PathVariable(value="id_viaje")Long id_viaje) {
		
		if(id_viaje != null) {
			viajeService.delete(id_viaje);
		}
		return "redirect:/Viajes";
	}
	
	@RequestMapping(value="/formViajBuscar")
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
				
				Page<Viaje> viajespage = viajeService.ViajesElemAreaPage(usus.getAdscripcion().getId_adscripcion(), elemento, pageRequest);
				
				PageRender<Viaje> pageRender = new PageRender<>("/formViajBuscar?elemento="+elemento, viajespage);
				model.addAttribute("viajes",viajespage);
				model.addAttribute("page",pageRender);
				model.addAttribute("elemento",elemento);
				return "Viajes";
		 };
			
			 Page<Viaje> viajespage= viajeService.ViajeElemPage("%"+elemento+"%", pageRequest);			 			
						 
			PageRender<Viaje> pageRender = new PageRender<>("/formViajBuscar?elemento="+elemento, viajespage);
			model.addAttribute("viajes",viajespage);
			model.addAttribute("page",pageRender);
			model.addAttribute("elemento",elemento);	
			return "Viajes";
		}else{
			return "redirect:/Viajes";
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
