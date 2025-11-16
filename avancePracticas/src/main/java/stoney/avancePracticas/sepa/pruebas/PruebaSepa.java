package stoney.avancePracticas.sepa.pruebas;

import java.time.LocalDateTime;

import stoney.avancePracticas.sepa.SEPAGenerator;
import stoney.avancePracticas.sepa.dominio.*;

public class PruebaSepa {

	public static void main(String[] args) throws Exception {
		// Crear acreedor 1
        SEPAParty creditor1 = new SEPAParty.Builder(
            "Proveedor A S.L.",
            "ES1234567890123456789012",
            "CAIXESBBXXX"
        )
        .withAddress("ES", "Avenida Secundaria 456")
        .build();
        
        // Crear acreedor 2
        SEPAParty creditor2 = new SEPAParty.Builder(
            "Proveedor B Ltd.",
            "ES9876543210987654321098",
            "SABADELLXXX"
        )
        .build();
        
        // Crear deudor (ordenante)
        SEPAParty debtor = new SEPAParty.Builder(
            "Mi Empresa S.L.",
            "ES9121000418450200051332",
            "BBVAESMMXXX"
        )
        .withAddress("ES", "Calle Principal 123")
        .build();
        
        // Crear información de pago con transacciones
        SEPAPaymentInfo paymentInfo = new SEPAPaymentInfo.Builder("PYT-001", debtor)
            .addTransaction(
                new SEPATransaction.Builder("TRX-001", 100050, creditor1)
                    .withConcept("Factura 001/2025")
                    .build()
            )
            .addTransaction(
                new SEPATransaction.Builder("TRX-002", 250000, creditor2)
                    .withConcept("Factura 002/2025")
                    .build()
            )
            .build();
        
        // Crear documento SEPA completo
        SEPADocument document = new SEPADocument.Builder("MSG-001-2025")
        	.addInitiator("Mi Empresa S.L.", "ID-EMP-2025")
            .withCreationDateTime(LocalDateTime.now())
            .addPaymentInfo(paymentInfo)
            .build();
        
        // Generar XML
        SEPAGenerator generator = new SEPAGenerator(document);
        String xml = generator.generateXML();
        System.out.println(xml);
        
        // Guardar a archivo
        generator.saveToFile("pago_sepa.xml");
        System.out.println("Archivo SEPA generado: pago_sepa.xml");
	}

}
