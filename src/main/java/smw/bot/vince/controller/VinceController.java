package smw.bot.vince.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import smw.bot.vince.model.Update;

@RestController
public class VinceController {
	
	@GetMapping("/hello")
	public ResponseEntity<String> getNextSession(){
		return ResponseEntity.ok("coming soon");
	}
	
	@PostMapping(path = "/f29ca76cdbaec82d5819ce9f4a52773f/updates", consumes = "application/json")
	public ResponseEntity<String> updatingWebHook(/*@RequestBody Update update*/){
		return ResponseEntity.ok("messaggio ricevuto :)");
	}

}
