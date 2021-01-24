package smw.bot.vince.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import smw.bot.vince.model.tg.Update;
import smw.bot.vince.service.VinceService;

@RestController
public class VinceController {
	
	private final VinceService service;
	private final Logger logger;	
	
	@Autowired
	public VinceController(VinceService service) {
		this.service = service;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@GetMapping("/wake-up")
	public ResponseEntity<String> getNextSession(){
		return ResponseEntity.ok("coming soon");
	}
	
	@PostMapping(path = "/f29ca76cdbaec82d5819ce9f4a52773f/updates", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> updatingWebHook(@RequestBody Update update){
		this.logger.info("RICEVUTO: {}", update);
		ResponseEntity<String> response = service.acceptUpdate(update); 
		this.logger.info("INVIATO: {}", response);
		return response;
	}

}
