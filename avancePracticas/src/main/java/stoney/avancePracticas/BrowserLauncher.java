package stoney.avancePracticas; // Asegúrate de que coincida con tu paquete

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.awt.Desktop;
import java.net.URI;

@Component
public class BrowserLauncher {

    // Cambia esto por la URL de tu frontend
    // Si usas VS Code Live Server será algo como "http://127.0.0.1:5500/index.html"
    // Si sirves el HTML desde Spring Boot será "http://localhost:8080"
    private static final String FRONTEND_URL = "http://localhost:8080/html/index.html"; 

    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() {
        // Configuramos la propiedad headless a false para permitir GUI
        System.setProperty("java.awt.headless", "false");

        // Comprobamos si el sistema soporta la API de escritorio
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(FRONTEND_URL));
                consoleLog("Navegador lanzado correctamente: " + FRONTEND_URL);
            } catch (Exception e) {
                consoleLog("Error al intentar abrir el navegador: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            consoleLog("El sistema no soporta la apertura automática del navegador.");
        }
    }

    private void consoleLog(String msg) {
        System.out.println("🚀 [BrowserLauncher]: " + msg);
    }
}