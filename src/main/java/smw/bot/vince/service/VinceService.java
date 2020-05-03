package smw.bot.vince.service;

import org.springframework.http.ResponseEntity;

import smw.bot.vince.model.Update;

public interface VinceService {
	
	ResponseEntity<String> acceptUpdate(Update update);

}
