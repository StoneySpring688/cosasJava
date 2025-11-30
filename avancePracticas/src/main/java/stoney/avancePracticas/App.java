package stoney.avancePracticas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        // FORMA ANTIGUA: SpringApplication.run(TuAplicacionApplication.class, args);
        
        // FORMA NUEVA (Para permitir abrir navegador):
        new SpringApplicationBuilder(App.class)
                .headless(false) // <--- ESTA ES LA CLAVE
                .run(args);
    }
}