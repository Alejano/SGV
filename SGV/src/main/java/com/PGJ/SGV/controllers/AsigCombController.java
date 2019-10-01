package com.PGJ.SGV.controllers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.PGJ.SGV.models.entity.AsigCombustible;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.IAsigComService;
import com.PGJ.SGV.service.IVehiculoService;


@Controller
public class AsigCombController {

	@Autowired
	private IAsigComService AsigCombus;
	@Autowired
	private IVehiculoService vehiculoService;
	
	public static String placaURL; 
	
	@RequestMapping(value="/ListarCombustible/{placa}", method = RequestMethod.GET)
	public String listar(@PathVariable(value="placa") String placa,Model model) {
		
		List<AsigCombustible> combustiblePlaca = new ArrayList<AsigCombustible>();
		var user="";
		if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";	model.addAttribute("usuario",user);}else {if(hasRole("ROLE_USER")) user = "ROLE_USER";model.addAttribute("usuario",user);};
		
		combustiblePlaca = AsigCombus.findPlaca(placa);
		model.addAttribute("titulo","Listado de Combustible");
		model.addAttribute("combustible",combustiblePlaca);
		
		return "Combustibles";
	}
		
	
	@RequestMapping(value="/formComb/Ag/{placa}", method = RequestMethod.GET)
	public String crear(@PathVariable(value="placa") String placa, Map<String,Object> model) {
		
		AsigCombustible combustible = new AsigCombustible();
		Vehiculo vehiculo = new Vehiculo();
		
		vehiculo.setPlaca(placa);
		combustible.setVehiculo(vehiculo);
		
		model.put("combustible",combustible );				
		model.put("titulo", "Formulario de Combustible");
							
		return "formComb";
	}
	
	@RequestMapping(value="/formComb/{id_asignacion}")
	public String editar(@PathVariable(value="id_asignacion") Long id_asignacion,Map<String,Object> model,Model model2 ) {
		AsigCombustible combustible = null;		
		
		if(!id_asignacion.equals(null)) {			
			combustible = AsigCombus.findOne(id_asignacion);
		}else {
			return "redirect:/ListarCombustible";
		}
		model2.addAttribute("placa",combustible.getVehiculo().getPlaca());
		model.put("combustible",combustible );				
		model.put("titulo", "Editar Combustible");
		return "formComb";
	}
	
	
	@RequestMapping(value="/formComb",method = RequestMethod.POST)
	public String guardar(AsigCombustible combustible){
		Vehiculo vehiculo = null;	
		vehiculo = vehiculoService.findOne(combustible.getVehiculo().getPlaca());	
		combustible.setVehiculo(vehiculo);
		AsigCombus.save(combustible);			
								
		return "redirect:/ListarCombustible/"+vehiculo.getPlaca();
	}
	
	@RequestMapping(value="/elimComb/{id_asignacion}/{placa}")
	public String eliminar (@PathVariable(value="id_asignacion")Long id_asignacion,@PathVariable(value="placa")String placa) {
		
		if(!id_asignacion.equals(null)) {
			AsigCombus.delete(id_asignacion);
		}
		return "redirect:/ListarCombustible/"+placa;
	}
	
	@RequestMapping(value="/Combustibles", method = RequestMethod.GET)
	public String listarCA(Map<String,Object> model, @RequestParam(value="placa")String placa,Model model2) throws Exception {
		
		var user="";
		if(hasRole("ROLE_ADMIN")) {
			user ="ROLE_ADMIN";						
			model.put("usuario",user);
		}else {
			if(hasRole("ROLE_USER")) 
				user = "ROLE_USER";
				model.put("usuario",user);				
			}
		
		placaURL=placa;
		List<AsigCombustible> combustible = new ArrayList<AsigCombustible>();
		List<AsigCombustible> combustiblePlaca = new ArrayList<AsigCombustible>();
		combustible = AsigCombus.findAll();	
		
		for(AsigCombustible comb: combustible) {
			if(comb.getVehiculo().getPlaca().equals(placaURL)) {
											
				combustiblePlaca.add(comb);
				}
		}
					
	    model2.addAttribute("placa", placa);
		model.put("combustible", combustiblePlaca);
		model.put("titulo", "Formulario de Combustible");
							
		
		
		
		return "Combustibles";
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
