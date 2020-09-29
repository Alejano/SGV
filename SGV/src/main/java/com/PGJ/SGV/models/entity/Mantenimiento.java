package com.PGJ.SGV.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mantenimientos")
public class Mantenimiento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_mantenimiento;

	private Double costo_mantenimiento;
	private String fecha_solicitud;
	private String fecha_diagnostico;
	private String fecha_ingreso;
	private String fecha_promesa_entrega;
	private String fecha_entrega;
	private String fecha_orden;
	private int no_orden;
	private String observaciones;
	private Double kilometraje;	
	private String concepto_reparacion;
	private int dias_permanencia;
	private String tipo_mantenimiento;
	private int dias_retardo;
	private String fecha_oficio;
	private String no_oficio;
	private String sancion;
	private String motivo;
	private String url_documentacion;

	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;

	@ManyToOne(fetch = FetchType.LAZY)
	private Taller talleres;

	public Mantenimiento() {
		vehiculo = new Vehiculo();
		talleres = new Taller();
	}	

	public Taller getTalleres() {
		return talleres;
	}

	public void setTalleres(Taller talleres) {
		this.talleres = talleres;
	}

	public String getFecha_oficio() {
		return fecha_oficio;
	}

	public void setFecha_oficio(String fecha_oficio) {
		this.fecha_oficio = fecha_oficio;
	}

	public String getNo_oficio() {
		return no_oficio;
	}

	public void setNo_oficio(String no_oficio) {
		this.no_oficio = no_oficio;
	}

	public Taller getTaller() {
		return talleres;
	}

	public void setTaller(Taller taller) {
		this.talleres = taller;
	}

	public Long getId_mantenimiento() {
		return id_mantenimiento;
	}

	public void setId_mantenimiento(Long id_mantenimiento) {
		this.id_mantenimiento = id_mantenimiento;
	}

	public Double getCosto_mantenimiento() {
		return costo_mantenimiento;
	}

	public void setCosto_mantenimiento(Double costo_mantenimiento) {
		this.costo_mantenimiento = costo_mantenimiento;
	}

	public String getFecha_solicitud() {
		return fecha_solicitud;
	}

	public void setFecha_solicitud(String fecha_solicitud) {
		this.fecha_solicitud = fecha_solicitud;
	}

	public String getFecha_diagnostico() {
		return fecha_diagnostico;
	}

	public void setFecha_diagnostico(String fecha_diagnostico) {
		this.fecha_diagnostico = fecha_diagnostico;
	}

	public String getFecha_ingreso() {
		return fecha_ingreso;
	}

	public void setFecha_ingreso(String fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	public String getFecha_promesa_entrega() {
		return fecha_promesa_entrega;
	}

	public void setFecha_promesa_entrega(String fecha_promesa_entrega) {
		this.fecha_promesa_entrega = fecha_promesa_entrega;
	}

	public String getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public String getFecha_orden() {
		return fecha_orden;
	}

	public void setFecha_orden(String fecha_orden) {
		this.fecha_orden = fecha_orden;
	}

	public int getNo_orden() {
		return no_orden;
	}

	public void setNo_orden(int no_orden) {
		this.no_orden = no_orden;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Double getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(Double kilometraje) {
		this.kilometraje = kilometraje;
	}

	public String getConcepto_reparacion() {
		return concepto_reparacion;
	}

	public void setConcepto_reparacion(String concepto_reparacion) {
		this.concepto_reparacion = concepto_reparacion;
	}

	public int getDias_permanencia() {
		return dias_permanencia;
	}

	public void setDias_permanencia(int dias_permanencia) {
		this.dias_permanencia = dias_permanencia;
	}

	public String getTipo_mantenimiento() {
		return tipo_mantenimiento;
	}

	public void setTipo_mantenimiento(String tipo_mantenimiento) {
		this.tipo_mantenimiento = tipo_mantenimiento;
	}

	public int getDias_retardo() {
		return dias_retardo;
	}

	public void setDias_retardo(int dias_retardo) {
		this.dias_retardo = dias_retardo;
	}

	public String getSancion() {
		return sancion;
	}

	public void setSancion(String sancion) {
		this.sancion = sancion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
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

	public String getUrl_documentacion() {
		return url_documentacion;
	}

	public void setUrl_documentacion(String url_documentacion) {
		this.url_documentacion = url_documentacion;
	}

	private static final long serialVersionUID = 1L;

}
