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
@Table(name = "acombus")
public class AsigCombustible implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_asignacion;		
	private String mes;
	private String ano;
	private Double presupuesto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;
	

	public Long getId_asignacion() {
		return id_asignacion;
	}

	public void setId_asignacion(Long id_aignacion) {
		this.id_asignacion = id_aignacion;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}


	private static final long serialVersionUID = 1L;
}
