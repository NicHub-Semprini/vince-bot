package smw.bot.vince.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import smw.bot.vince.model.Update;
import smw.bot.vince.service.VinceService;

@Service
public class VinceServiceImpl implements VinceService {
	
	private static LocalDateTime nextSession;
	private final Logger logger;
	private final DateTimeFormatter formatterIn;
	private final DateTimeFormatter formatterOut;
	private final static String DATE_TIME_FORMAT_IN= "dd/MM/yyyy HH:mm";
	private final static String DATE_TIME_FORMAT_OUT= "'Prossima sessione:' EEEE d MMMM 'ore' HH:mm";
	private final static String SET_SESSIONE_COMMAND = "/set_sessione";
	private final static String GET_SESSIONE_COMMAND = "/sessione";

	public VinceServiceImpl() {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.formatterIn = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_IN);
		this.formatterOut = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_OUT);
	}
	
	@Override
	public String parseCommand(Update update) {
		String text = update.getMessage().getText();
		this.logger.info("TEXT: {}", text);
		String[] words = text.split(" ");
		switch(words[0]) {
			case SET_SESSIONE_COMMAND:
				this.logger.info("SET_SESSIONE SERVICE");
				try {
					LocalDateTime data = LocalDateTime.from(formatterIn.parse(words[1] + " " + words[2]));
					setSessione(data);
					return formatterOut.format(nextSession);
				} catch(DateTimeParseException e) {
					logger.error(e.getMessage());
					return createErrorResponse(SET_SESSIONE_COMMAND, new String[] {DATE_TIME_FORMAT_IN});
				}
			case GET_SESSIONE_COMMAND:
				this.logger.info("GET_SESSIONE SERVICE");
				return formatterOut.format(nextSession);
			default:
				this.logger.info("ECHO SERVICE");
				return text;
		}
//		if(words[0].equals(SET_SESSIONE_COMMAND)) {
//			this.logger.info("SET_SESSIONE SERVICE");
//			try {
//				LocalDateTime data = (LocalDateTime) formatterIn.parse(words[1] + " " + words[2]);
//				setSessione(data);
//				return formatterOut.format(nextSession);
//			} catch(DateTimeParseException e) {
//				logger.error(e.getMessage());
//				return createErrorResponse(SET_SESSIONE_COMMAND, new String[] {DATE_TIME_FORMAT_IN});
//			}
//		} else {
//			this.logger.info("ECHO SERVICE");
//			return text;
//		}
	}
	
	private static void setSessione(LocalDateTime value) {
		nextSession = value;
	}
	
	private String createErrorResponse(String command, String[] params) {
		StringBuilder sb = new StringBuilder("Usage: ");
		sb.append(command);
		for(String param : params) {
			sb.append(" " + param);
		}
		return sb.toString();
	}

}
