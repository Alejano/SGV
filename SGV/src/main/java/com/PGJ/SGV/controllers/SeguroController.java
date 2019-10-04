package com.PGJ.SGV.controllers;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import com.PGJ.SGV.models.entity.Seguro;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.service.ISeguroService;
import com.PGJ.SGV.service.IUploadFileService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.service.IVehiculoService;
import com.PGJ.SGV.util.paginador.PageRender;




@Controller
public class SeguroController {
	List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
	List<Seguro> seguros = new ArrayList<Seguro>();
	static String user="";
	@Autowired
	private ISeguroService seguroService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private IUsuarioService usuarioService;
	
	@RequestMapping(value="/Seguros", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page,Model model,Authentication authentication) {	
	
		//List<Seguro> segurosArea = new ArrayList<Seguro>();
				
			
		var ads="";			
		if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";	model.addAttribute("usuario",user);
		}else {if(hasRole("ROLE_USER")) user = "ROLE_USER"; model.addAttribute("usuario",user);}
		
		ads = authentication.getName();
		Pageable pageRequest = PageRequest.of(page, 100);
		
		if(user.equals("ROLE_USER")){
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			
			//segurosArea = seguroService.FindSeguroArea(usus.getAdscripcion().getId_adscripcion());
			Page<Seguro> SeguroAreaPage = seguroService.FindSeguroAreaPage(usus.getAdscripcion().getId_adscripcion(), pageRequest);
			PageRender<Seguro> SeguroRenderArea = new PageRender<>("/Seguros",SeguroAreaPage);
			if(seguroService.totalSeguros()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
			model.addAttribute("seguros",SeguroAreaPage);
			model.addAttribute("page",SeguroRenderArea);
			return "Seguros";
		}
		
		vehiculos = vehiculoService.findAll();
		Page<Seguro> SeguroPage = seguroService.findAll(pageRequest);
		PageRender<Seguro> SeguroRender = new PageRender<>("/Seguros",SeguroPage);
		if(seguroService.totalSeguros()>=7) {model.addAttribute("tamano","mostrar");}else{model.addAttribute("tamano","no mostrar");};
		//seguros =seguroService.findAll();		
		
		model.addAttribute("titulo","Listado de Seguros");
		model.addAttribute("seguros",SeguroPage);
		model.addAttribute("page",SeguroRender);
		
		return "Seguros";
	}
		
	
	@RequestMapping(value="/formSeg")
	public String crear(Map<String,Object> model,Authentication authentication) {
		var ads="";						
		ads = authentication.getName();
		var user="";
		
		
		if(hasRole("ROLE_ADMIN")) {user ="ROLE_ADMIN";model.put("usuario",user);}else {if(hasRole("ROLE_USER")) {user = "ROLE_USER";model.put("usuario",user);};};
		if(user.equals("ROLE_USER")) {
			List<Vehiculo> Ve = new ArrayList<Vehiculo>();
			Usuario usus = new Usuario();
			usus = usuarioService.findbyAdscripcion(ads);
			Ve = vehiculoService.findVehiculosArea(usus.getAdscripcion().getId_adscripcion());
			Seguro seguro = new Seguro();			
			model.put("vehiculos", Ve);
			model.put("seguros", seguro);
			model.put("titulo", "Formulario de Seguros");
			return "formSeg";
		};
		
		Seguro seguro = new Seguro();
		vehiculos = vehiculoService.findAll();
		model.put("vehiculos", vehiculos);
		model.put("seguros", seguro);
		model.put("titulo", "Formulario de Seguros");
							
		return "formSeg";
	}
	
	@RequestMapping(value="/formSeg/{id}")
	public String editar(@PathVariable(value="id") Long id,Map<String,Object>model) {
		
		Seguro seguro = null;
		
		if(!id.equals(null)) {
			seguro = seguroService.findOne(id);
		}else {
			return "redirect:/seguros";
		}
		
		model.put("vehiculos", vehiculos);
		model.put("seguros",seguro);
		model.put("titulo", "Editar Seguro");
		return "formSeg";
	}
	
	@RequestMapping(value = "/verSeguro/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Seguro seguro = null;
		String documento = "existe";
		if (!id.equals(null)) {
			seguro = seguroService.findOne(id);
			if(seguro.getDocumento().isBlank()) {documento = "";};
		} else {
			return "redirect:/Seguros";
		}

		model.put("vehiculo", vehiculos);
		model.put("documento", documento);
		model.put("seguro", seguro);
		model.put("titulo", "Ver Seguro");
		return "verSeguro";
	}
	
	
	@RequestMapping(value = "/formSeg", method = RequestMethod.POST)
	public String guardar(Seguro seguro, @RequestParam("file") MultipartFile documento) {

		if (!documento.isEmpty()) {

			if (seguro.getId() != null && seguro.getId() > 0 && seguro.getDocumento() != null
					&& seguro.getDocumento().length() > 0) {
				System.out.println("entro al if");
				uploadFileService.delete(seguro.getDocumento());
			}
			String nombreUnico = null;
			try {
				nombreUnico = uploadFileService.copy(documento);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			seguro.setDocumento(nombreUnico);
			seguroService.save(seguro);
		}
		System.out.println(documento);

		return "redirect:Seguros";
	}

	
	@RequestMapping(value="/elimSeg/{id}/{documento}")
	public String eliminar (@PathVariable(value="id")Long id,@PathVariable(value="documento")String documento) {
		
		if(id != null) {
			seguroService.delete(id);	
			if(documento != "") {
				uploadFileService.delete(documento);
			}
		}
		return "redirect:/Seguros";
	}
	@RequestMapping(value="/elimSeg/{id}/")
	public String eliminar (@PathVariable(value="id")Long id) {
		
		if(id != null) {
			seguroService.delete(id);				
		}
		return "redirect:/Seguros";
	}
	
	@RequestMapping(value="/formSegBuscar")
	public String Buscartabla(@RequestParam(name="page", defaultValue = "0") int page,Authentication authentication,
			@RequestParam(value="elemento") String elemento,Model model){						 
		Pageable pageRequest = PageRequest.of(page, 100);
		 var ads="";		
		 ads = authentication.getName();
		 
			 if(user.equals("ROLE_ADMIN")) {model.addAttribute("usuario",user);
				}else {if(user.equals("ROLE_USER")) { model.addAttribute("usuario",user);}};
			 
		if(!elemento.isBlank()) {			
							if(isValidDouble(elemento)) {
								Double Dato = Double.parseDouble(elemento);
									DecimalFormat formt = new DecimalFormat("0");
									elemento = formt.format(Dato);
									elemento = elemento.replaceAll(",","");	
							};
			
							
					if(user.equals("ROLE_USER")) {
						 Usuario usus = new Usuario();
							usus = usuarioService.findbyAdscripcion(ads);
						
								Page<Seguro> segurospage = seguroService.FindSegElemAreaPage(usus.getAdscripcion().getId_adscripcion(),"%"+elemento+"%", pageRequest);
							PageRender<Seguro> pageRender = new PageRender<>("/formSegBuscar?elemento="+elemento, segurospage);
							model.addAttribute("seguros",segurospage);
							model.addAttribute("page",pageRender);
							model.addAttribute("elemento",elemento);	
							return "Seguros";
					};
					 	Page<Seguro> segurospage= seguroService.FindSegElemPage("%"+elemento+"%", pageRequest);			 									
					PageRender<Seguro> pageRender = new PageRender<>("/formSegBuscar?elemento="+elemento, segurospage);
					model.addAttribute("seguros",segurospage);
					model.addAttribute("page",pageRender);
					model.addAttribute("elemento",elemento);	
					return "Seguros";
					
		}else{
			return "redirect:/Seguros";
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