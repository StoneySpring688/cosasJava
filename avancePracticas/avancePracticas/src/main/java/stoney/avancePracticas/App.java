package stoney.avancePracticas;

import org.slf4j.Logger;

import stoney.avancePracticas.casosDeUso.HacerExcel;



/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws Exception
    {
        logger.info("Hello World!" );
        logger.debug("This is a debug message.");
        logger.info("Fecha y hora actual: {}", java.time.LocalDateTime.now());
        testExcel();
        
        
    }
    private static void testExcel() {
		try {
			HacerExcel.hacerTablaBasica();
			HacerExcel.hacerTablaCompleta();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
