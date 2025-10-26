package jaxb;
import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import actas.modelo.*;

public class Programa3 {
	public static void main(String[] args) throws Exception {
		JAXBContext contexto = JAXBContext.newInstance(Acta.class);
		
		Marshaller marshaller = contexto.createMarshaller();
		
		marshaller.setProperty("jaxb.formatted.output", true);
		Unmarshaller unmarshaller = contexto.createUnmarshaller();
		
		Acta acta2 = (Acta) unmarshaller.unmarshal(new File("xml/acta.xml"));
		LinkedList<Calificacion> cals = acta2.getCalificaciones();
		Double sumador=0.0;
		for(int i = 0; i< cals.size(); i++) {
			sumador+=cals.get(i).getNota();
		}
		Double media = sumador/cals.size();
		System.out.println("media: "+media);
		LocalDate ahora = LocalDate.now();
		LocalDate haceUnMes = ahora.minusMonths(1);

		long numDiligenciasUltimoMes = acta2.getDiligencias().stream()
		    .filter(d -> {
		        LocalDate fecha = d.getFechaLocal();
		        return (fecha.isAfter(haceUnMes) || fecha.isEqual(haceUnMes)) && fecha.isBefore(ahora.plusDays(1));
		    })
		    .count();

		System.out.println("Número de diligencias en el último mes: " + numDiligenciasUltimoMes);
		
		/*
		 * Utiliza JAXB para crear un acta que sea el resultado de incrementar en 0,5 puntos la nota
		 *  de todos los alumnos (la nota no debe superar 10).
		 *   El resultado debe ser almacenado en un otro documento (por ejemplo, "acta-modificado.xml").
		 * */
		
		acta2.getCalificaciones().stream()
	    .forEach(c -> {
	        if (c.getNota() + 0.5 <= 10) {
	            c.setNota(c.getNota() + 0.5);
	        } else {
	            c.setNota(10.0);
	        }
	    });
		//escribir en el fchero
		marshaller.marshal(acta2,new File("xml/acta-modificado.xml"));
		/*
		 * Utiliza JAXB para construir el siguiente documento del acta:
		 * 
		 * Convocatoria: febrero. Curso: 2024 Asignatura: 1092 Calificación: (nif:
		 * 17309648D, nota: 10) Calificación: (nif: 59375279C, nombre: Pepe, nota: 8)
		 * Diligencia: (fecha: 2024-09-12, nif: 59375279C, nota: 9) Finalmente, almacena
		 * el documento en disco.
		 */
		Acta acta = new Acta();
		acta.setConvocatoria("febrero");
		acta.setCurso("2024");
		acta.setAsignatura("1092");
		Calificacion c1 = new Calificacion();
		c1.setNif("17309648D");
		c1.setNota(10.0);
		Calificacion c2 = new Calificacion();
		c2.setNif("59375279C");
		c2.setNota(8.0);
		c2.setNombre("pepe");
		LinkedList<Calificacion> cs = new LinkedList<Calificacion>();
		cs.add(c2);
		cs.add(c1);
		Diligencia d1 = new Diligencia();
		LocalDate date = LocalDate.parse("2024-09-12");
		d1.setFechaLocal(date);
		d1.setNif("59375279C");
		d1.setNota(9.0);
		acta.setCalificaciones(cals);
		LinkedList<Diligencia> ds = new LinkedList<Diligencia>();
		ds.add(d1);
		acta.setDiligencias(ds);
		marshaller.marshal(acta, new File("xml/acta-nueva.xml"));
		
	}
}
