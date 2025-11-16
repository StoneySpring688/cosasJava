package stoney.avancePracticas.excel;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TablaBasica {
	public static void hacerTablaBasica() throws Exception {
        // 1. Crear workbook (archivo Excel)
        Workbook workbook = new XSSFWorkbook();
        
        // 2. Crear una hoja
        Sheet sheet = workbook.createSheet("Gastos");
        
        // 3. Crear una fila
        Row row = sheet.createRow(0);
        
        // 4. Crear una celda
        Cell cell = row.createCell(0);
        cell.setCellValue("Hola Excel");
        
        // 5. Guardar archivo
        try (FileOutputStream fos = new FileOutputStream("archivo.xlsx")) {
            workbook.write(fos);
        }
        workbook.close();
    }
}
