package smw.bot.vince.service;

import org.springframework.http.ResponseEntity;

import smw.bot.vince.model.tg.Update;

public interface VinceService {
	
	ResponseEntity<String> acceptUpdate(Update update);

}
