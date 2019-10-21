package com.PGJ.SGV.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.PGJ.SGV.models.entity.Adscripcion;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IAdscripcionService;
import com.PGJ.SGV.service.IMantenimientoService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.service.IViajeService;
@Controller
public class DashboardController {
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IAdscripcionService adscripcionService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IViajeService viajeService;
	@Autowired
	private IMantenimientoService mantenimientoService;
	
	@RequestMapping(value="/Dashboard", method = RequestMethod.GET)
	public String listar(Model model, Authentication authentication) {				
		List<Adscripcion> adscripciones = new ArrayList<Adscripcion>();
		List<Datos> DatosGenerales = new ArrayList<Datos>();
		adscripciones = adscripcionService.findAll();
		int numeromaximo=0;
		for(Adscripcion ads:adscripciones) {
			//datos de adscripciones
			if(numeromaximo <= 5) {
			Datos D=new Datos();
			D.setAdscripcion(ads.getId_adscripcion());
			D.setNombre_adscripcion(ads.getNombre_adscripcion());
			D.setVehiculos(vehiculoService.totalVehiculoArea(ads.getId_adscripcion()));
			D.setViajes(viajeService.TotalViajesArea(ads.getId_adscripcion()));
			D.setMantenimientos(mantenimientoService.TotalMantenimientoArea(ads.getId_adscripcion()));
			DatosGenerales.add(D);
			numeromaximo++;
			}			
		}
		numeromaximo=0;
		
		String usuario = authentication.getName();
		Usuario usus = new Usuario();
		usus = usuarioService.findbyAdscripcion(usuario);
		model.addAttribute("DatosGenerales",DatosGenerales);
		model.addAttribute("Usuario",usus.getNombre()+" "+usus.getApellido1());				
		return "Dashboard";
	}
	
	public class Datos{
		private Long Adscripcion;
		private String nombre_adscripcion;
		private int vehiculos;
		private int viajes;
		private int Mantenimientos;
		public Long getAdscripcion() {
			return Adscripcion;
		}
		public void setAdscripcion(Long adscripcion) {
			Adscripcion = adscripcion;
		}
		public int getVehiculos() {
			return vehiculos;
		}
		public void setVehiculos(int vehiculos) {
			this.vehiculos = vehiculos;
		}
		public int getViajes() {
			return viajes;
		}
		public void setViajes(int viajes) {
			this.viajes = viajes;
		}
		public int getMantenimientos() {
			return Mantenimientos;
		}
		public void setMantenimientos(int mantenimientos) {
			Mantenimientos = mantenimientos;
		}
		public String getNombre_adscripcion() {
			return nombre_adscripcion;
		}
		public void setNombre_adscripcion(String nombre_adscripcion) {
			this.nombre_adscripcion = nombre_adscripcion;
		}			
		
		
	}
		
	
	
}
