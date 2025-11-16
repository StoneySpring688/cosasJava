package stoney.avancePracticas.excel;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

public class ExcelTablaCompleta {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ExcelTablaCompleta.class);
	
	public static void hacerTablaCompleta() throws Exception {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Gastos");

		// Crear estilos
		CellStyle headerStyle = crearEstiloEncabezado(workbook);
		CellStyle dataStyle = crearEstiloDatos(workbook);
		CellStyle moneyStyle = crearEstiloMoneda(workbook);
		CellStyle dateStyle = crearEstiloFecha(workbook);

		// Crear encabezados
		String[] encabezados = {"ID", "Descripción", "Importe", "Fecha", "Categoría"};
		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < encabezados.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(encabezados[i]);
			cell.setCellStyle(headerStyle);
		}

		// Crear datos de ejemplo
		List<Gasto> gastos = obtenerDatos();

		// Llenar tabla con datos
		int fila = 1;
		for (Gasto gasto : gastos) {
			Row row = sheet.createRow(fila);

			// ID
			Cell cell0 = row.createCell(0);
			cell0.setCellValue(gasto.getId());
			cell0.setCellStyle(dataStyle);

			// Descripción
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(gasto.getDescripcion());
			cell1.setCellStyle(dataStyle);

			// Importe (con formato moneda)
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(gasto.getImporte());
			cell2.setCellStyle(moneyStyle);

			// Fecha
			Cell cell3 = row.createCell(3);
			cell3.setCellValue(gasto.getFecha().toString());
			cell3.setCellStyle(dateStyle);

			// Categoría
			Cell cell4 = row.createCell(4);
			cell4.setCellValue(gasto.getCategoria());
			cell4.setCellStyle(dataStyle);

			fila++;
		}

		// Ajustar ancho de columnas automáticamente
		for (int i = 0; i < encabezados.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Guardar
		try (FileOutputStream fos = new FileOutputStream("gastos.xlsx")) {
			workbook.write(fos);
		}
		workbook.close();
		logger.info("Archivo creado: gastos.xlsx");
	}

	// ==================== ESTILOS ====================

	private static CellStyle crearEstiloEncabezado(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();

		// Fuente
		Font font = workbook.createFont();
		font.setBold(true);
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		// Fondo azul
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Alineación
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		// Bordes
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		return style;
	}

	private static CellStyle crearEstiloDatos(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();

		// Bordes
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		// Alineación
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		return style;
	}

	private static CellStyle crearEstiloMoneda(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();

		// Formato moneda
		style.setDataFormat(workbook.createDataFormat().getFormat("€ #,##0.00"));

		// Bordes
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		// Alineación derecha
		style.setAlignment(HorizontalAlignment.RIGHT);

		return style;
	}

	private static CellStyle crearEstiloFecha(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();

		// Formato fecha
		style.setDataFormat(workbook.createDataFormat().getFormat("dd/mm/yyyy"));

		// Bordes
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		// Alineación
		style.setAlignment(HorizontalAlignment.CENTER);

		return style;
	}

	// ==================== DATOS ====================

	private static List<Gasto> obtenerDatos() {
		return Arrays.asList(
				new Gasto("001", "Gasolina", 45.50, LocalDate.of(2025, 10, 20), "Transporte"),
				new Gasto("002", "Hotel", 120.00, LocalDate.of(2025, 10, 21), "Alojamiento"),
				new Gasto("003", "Comida", 35.75, LocalDate.of(2025, 10, 22), "Alimentación"),
				new Gasto("004", "Taxi", 25.00, LocalDate.of(2025, 10, 23), "Transporte"),
				new Gasto("005", "Conferencia", 200.00, LocalDate.of(2025, 10, 24), "Formación")
				);
	}

	// Clase interna para ejemplo
	static class Gasto {
		String id, descripcion, categoria;
		double importe;
		LocalDate fecha;

		Gasto(String id, String descripcion, double importe, LocalDate fecha, String categoria) {
			this.id = id;
			this.descripcion = descripcion;
			this.importe = importe;
			this.fecha = fecha;
			this.categoria = categoria;
		}

		String getId() { return id; }
		String getDescripcion() { return descripcion; }
		double getImporte() { return importe; }
		LocalDate getFecha() { return fecha; }
		String getCategoria() { return categoria; }
	}
}
