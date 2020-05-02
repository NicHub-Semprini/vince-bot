package smw.bot.vince.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		String uri = buildUri(update.getMessage().getChat().getId(), update.getMessage().getText());
		return restTemplate.postForEntity(uri, headers, String.class);
	}
	
	private String buildUri(Long chatId, String text) {
		StringBuilder sb = new StringBuilder(url).append("?").append("chat_id=").append(chatId).append("&").append("text=").append(text);
		return sb.toString();
	}

}
