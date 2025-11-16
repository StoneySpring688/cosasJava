package stoney.avancePracticas.casosDeUso;

import stoney.avancePracticas.excel.ExcelTablaCompleta;
import stoney.avancePracticas.excel.TablaBasica;

public class HacerExcel {
	public static void hacerTablaBasica() throws Exception {
		TablaBasica.hacerTablaBasica();
	}
	public static void hacerTablaCompleta() throws Exception {
		ExcelTablaCompleta.hacerTablaCompleta();
	}
}
