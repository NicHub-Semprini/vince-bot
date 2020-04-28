package smw.bot.vince.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VinceController {
	
	@GetMapping("/hello")
	public ResponseEntity<String> getNextSession(){
		return ResponseEntity.ok("coming soon");
	}

}
