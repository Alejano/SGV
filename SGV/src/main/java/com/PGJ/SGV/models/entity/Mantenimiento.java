package com.PGJ.SGV.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mantenimientos")
public class Mantenimiento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_mantenimiento;
	

	private Double kilometraje;
	private Double costo_mantenimiento;
	private boolean siniestro;
	private String fecha_ingreso;
	private String fecha_entrega;
	private String observaciones;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;

	public Long getId_mantenimiento() {
		return id_mantenimiento;
	}

	public void setId_mantenimiento(Long id_mantenimiento) {
		this.id_mantenimiento = id_mantenimiento;
	}

	public Double getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(Double kilometraje) {
		this.kilometraje = kilometraje;
	}

	public Double getCosto_mantenimiento() {
		return costo_mantenimiento;
	}

	public void setCosto_mantenimiento(Double costo_mantenimiento) {
		this.costo_mantenimiento = costo_mantenimiento;
	}

	public boolean getSiniestro() {
		return siniestro;
	}

	public void setSiniestro(boolean siniestro) {
		this.siniestro = siniestro;
	}

	public String getFecha_ingreso() {
		return fecha_ingreso;
	}

	public void setFecha_ingreso(String fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	public String getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

}
