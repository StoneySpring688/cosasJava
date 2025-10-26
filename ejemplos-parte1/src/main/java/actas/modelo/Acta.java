package actas.modelo;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;


import javax.xml.bind.annotation.*;


@XmlRootElement
public class Acta {
	
	private String convocatoria;
	private String curso;
	private String asignatura;
	
	private LinkedList<Calificacion> calificaciones;
	private LinkedList<Diligencia> diligencias;
	@XmlAttribute
	public String getConvocatoria() {
		return convocatoria;
	}
	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}
	@XmlAttribute
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	@XmlAttribute
	public String getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	@XmlElement(name="calificacion")
	public LinkedList<Calificacion> getCalificaciones() {
		return calificaciones;
	}
	public void setCalificaciones(LinkedList<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}
	@XmlElement(name="diligencia")
	public LinkedList<Diligencia> getDiligencias() {
		return diligencias;
	}
	public void setDiligencias(LinkedList<Diligencia> diligencias) {
		this.diligencias = diligencias;
	}
	

}
