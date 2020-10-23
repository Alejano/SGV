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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.PGJ.SGV.models.entity.Authority;
import com.PGJ.SGV.models.entity.LogsAudit;
import com.PGJ.SGV.models.entity.Taller;
import com.PGJ.SGV.models.entity.Usuario;
import com.PGJ.SGV.service.IAutoridadService;
import com.PGJ.SGV.service.ILogsAuditService;
import com.PGJ.SGV.service.ITallerService;
import com.PGJ.SGV.service.IUsuarioService;
import com.PGJ.SGV.utilities.ObtenHour;
import com.PGJ.SGV.utilities.SystemDate;

@Controller
public class TallerController {
	
	@Autowired
	private ITallerService tallerservice;
	@Autowired
	private IAutoridadService autoservice;
	@Autowired
	private IUsuarioService ususervice;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ILogsAuditService logsauditService;

	
	public static boolean editar = false;
	@RequestMapping(value="/Talleres", method = RequestMethod.GET)
	public String listar(Model model, Authentication authentication) {				
				
		List<Taller> talleres = new ArrayList<Taller>();
		
		talleres = tallerservice.findAll();
				
		model.addAttribute("talleres",talleres);		
		
		return "Talleres/Talleres";
	}
	
	@RequestMapping(value="/AddTaller")
	public String crear(Map<String,Object> model) {	
		editar=false;
		Taller taller = new Taller();				
		
		model.put("talleres", taller);
		return "Talleres/formTaller";
	}
	
	@RequestMapping(value="/AddTaller",method = RequestMethod.POST)
	public String guardar(Taller taller,Authentication authentication) {
		
		 var user="";
		 var no_user ="";
		 no_user = authentication.getName();
		 
		 if(hasRole("ROLE_ADMIN")) {
				user ="ROLE_ADMIN";
				}else {
					if(hasRole("ROLE_USER")) {
						user = "ROLE_USER";
					}
				}
		
		Authority auto = new Authority();
		auto = autoservice.findOne(3);
		Usuario Usu = new Usuario();
		Usu.setNo_empleado(taller.getNombre());
		Usu.setApellido1("");
		Usu.setApellido2("");
		Usu.setAuthority(auto);
		Usu.setEnabled(true);
		Usu.setFecha_alta(SystemDate.obtenFecha());
		String pass = ObtenPass(taller.getNo_contrato());
		Usu.setContrasena(pass);
		ususervice.save(Usu);
		tallerservice.save(taller);
		
		
		String valor_nuevo=taller.toString();
			
			//Auditoria
			
	     	LogsAudit logs = new LogsAudit();
	     	
	        logs.setId_usuario(no_user);
			logs.setTipo_role(user);
			logs.setFecha(SystemDate.obtenFecha());
			logs.setHora(ObtenHour.obtenHour());
			logs.setName_table("TALLERES");
			logs.setValor_viejo(valor_nuevo);
			logs.setTipo_accion("INSERT");
									
			logsauditService.save(logs);
	
		editar = false;	
		return "redirect:Talleres";
	}

	private String ObtenPass(String no_contrato) {
		String bcryptPassword="";
		for(int i=0; i<450; i++) {
		 bcryptPassword = passwordEncoder.encode(no_contrato);
			
		}
		
		return bcryptPassword;		
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
