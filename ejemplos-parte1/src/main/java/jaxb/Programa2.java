package jaxb;

import java.io.File;
import java.rmi.UnmarshalException;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import actas.modelo.Acta;
import actas.modelo.Calificacion;

public class Programa2 {

	public static void main(String[] args) throws Exception {
		
		JAXBContext contexto = JAXBContext.newInstance(Acta.class);
		
		Marshaller marshaller = contexto.createMarshaller();
		
		marshaller.setProperty("jaxb.formatted.output", true);
		Acta acta = new Acta();
		acta.setAsignatura("2567");
		acta.setConvocatoria("junio");
		acta.setCurso("2025/2026");
		Calificacion c = new Calificacion();
		c.setNif("12345678Z");
		c.setNombre("Maria");
		c.setNota(8d);
		Calificacion c2 = new Calificacion();
		c2.setNif("12345678Z");
		c2.setNombre("Pablo");
		c2.setNota(9d);
		
		LinkedList<Calificacion> lista = new LinkedList<>();
		lista.add(c);
		lista.add(c2);
		acta.setCalificaciones(lista);
		marshaller.marshal(acta,new File("xml/acta-jaxb.xml"));
		Unmarshaller unmarshaller = contexto.createUnmarshaller();
		Acta acta2 = (Acta) unmarshaller.unmarshal(new File("xml/acta-jaxb.xml"));
		System.out.println(acta2.toString());
		acta2.setConvocatoria("septiembre");
		marshaller.marshal(acta2, new File("xml/acta2-jaxb.xml"));
	}
}
