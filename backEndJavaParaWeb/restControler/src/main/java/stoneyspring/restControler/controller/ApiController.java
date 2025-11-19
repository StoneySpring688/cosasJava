package stoneyspring.restControler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	@GetMapping("/hola")
	public String holaMundo() {
		return "Hola Mundo";
	}
}
