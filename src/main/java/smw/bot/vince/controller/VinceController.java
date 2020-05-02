package smw.bot.vince.controller;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import smw.bot.vince.model.Update;

@RestController
public class VinceController {
	
	private final RestTemplate restTemplate;
	
	private final Logger logger;
	
	private final String url = "https://api.telegram.org/bot1001648084:AAHPcuvoo8gN-XAskF3jtsbBTRTQB2GP3x8/sendMessage";
	
	@Autowired
	public VinceController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@GetMapping("/hello")
	public ResponseEntity<String> getNextSession(){
		return ResponseEntity.ok("coming soon");
	}
	
	@PostMapping(path = "/f29ca76cdbaec82d5819ce9f4a52773f/updates", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> updatingWebHook(@RequestBody Update update){
		this.logger.info("Ricevuto: {}", update);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("chat_id", update.getMessage().getChat().getId()).queryParam("text", update.getMessage().getText());
		return restTemplate.postForEntity(builder.toUriString(), null, String.class);
	}

}
