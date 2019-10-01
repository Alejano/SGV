package com.PGJ.SGV.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "chofer")
public class Conductor implements Serializable {

	@Id
	private String no_empleado;

	@ManyToOne(fetch = FetchType.LAZY)
	private Adscripcion adscripcion;

	private String nombre;
	private String apellido1;
	private String apellido2;
	private Integer enabled;

	@OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Viaje> viajes;
	
	public Conductor() {
		viajes = new ArrayList<Viaje>();
		
	}

	public Adscripcion getAdscripcion() {
		return adscripcion;
	}

	public void setAdscripcion(Adscripcion adscripcion) {
		this.adscripcion = adscripcion;
	}

	public String getNo_empleado() {
		return no_empleado;
	}

	public void setNo_empleado(String no_empleado) {
		this.no_empleado = no_empleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}	
		
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public List<Viaje> getViajes() {
		return viajes;
	}

	public void setViajes(List<Viaje> viajes) {
		this.viajes = viajes;
	}
	public void adViaje(Viaje viaje) {
		viajes.add(viaje);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
}
