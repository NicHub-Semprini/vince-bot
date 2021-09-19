package smw.bot.vince.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smw.bot.vince.client.TelegramRestClient;
import smw.bot.vince.model.db.NextSessionMeeting;
import smw.bot.vince.model.tg.Message;
import smw.bot.vince.model.tg.ResponseMessage;
import smw.bot.vince.model.tg.Update;
import smw.bot.vince.model.tg.User;
import smw.bot.vince.repository.NextSessionMeetingRepository;
import smw.bot.vince.service.VinceService;

@Service
public class VinceServiceImpl implements VinceService {

	private final Logger log;
	private final DateTimeFormatter formatterIn;
	private final DateTimeFormatter formatterOut;
	private final TelegramRestClient restClient;
	private final NextSessionMeetingRepository nextSessionMeetingRepository;

	private final static String USERNAME = "VinceEarlLiamBot";
	private final static String HELLO_MESSAGE = "Signore e signori, sono qui per divertire!";
	private final static String DATE_TIME_FORMAT_IN = "dd/MM/yyyy HH:mm";
	private final static String DATE_TIME_FORMAT_OUT = "'Prossima sessione:' EEEE d MMMM 'ore' HH:mm";
	private final static String SET_SESSIONE_COMMAND = "/set_sessione";
	private final static String GET_SESSIONE_COMMAND = "/sessione";

	@Autowired
	public VinceServiceImpl(@RestClient TelegramRestClient restClient, NextSessionMeetingRepository nextSessionMeetingRepository) {
		this.restClient = restClient;
		this.nextSessionMeetingRepository = nextSessionMeetingRepository;
		this.log = LoggerFactory.getLogger(this.getClass());
		this.formatterIn = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_IN, Locale.ITALIAN);
		this.formatterOut = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_OUT, Locale.ITALIAN);
	}

	@Override
	public String acceptUpdate(Update update) {
		String text = update.getMessage().getText();
		String responseText = null;
		log.info("TEXT: {}", text);
		if (isBlank(text) && hasJoinedGroup(update.getMessage())) {
			log.info("JOINED_GROUP SERVICE");
			responseText = HELLO_MESSAGE;
		} else {
			responseText = parseCommand(text);
		}
		if (!isBlank(responseText)) {
			ResponseMessage response = new ResponseMessage(update.getMessage().getChat().getId(), responseText);
			log.info("Reply with: {}", response);
			Response telegramResponse = restClient.sendMessage(response);
			log.info("Received from telegram: {} {}", telegramResponse.getStatusInfo(), telegramResponse.getEntity());
			return telegramResponse.getEntity().toString();			
		}
		return null;
	}

	private String parseCommand(String text) {
		String[] words = text.split(" ");
		String result;
		switch (words[0]) {
		case SET_SESSIONE_COMMAND:
			log.info("SET_SESSIONE SERVICE");
			try {
				LocalDateTime nextSessionDate = LocalDateTime.from(formatterIn.parse(words[1] + " " + words[2]));
				NextSessionMeeting nextSessionMeeting = nextSessionMeetingRepository.findFirst().get();
				nextSessionMeeting.setNextMeeting(nextSessionDate);
				nextSessionMeeting = nextSessionMeetingRepository.save(nextSessionMeeting);
				result = formatterOut.format(nextSessionMeeting.getNextMeeting());
			} catch (Exception e) {
				log.error(e.toString());
				result = createErrorResponse(SET_SESSIONE_COMMAND, new String[] { DATE_TIME_FORMAT_IN });
			}
			break;
		case GET_SESSIONE_COMMAND:
			log.info("GET_SESSIONE SERVICE");
			try {
				NextSessionMeeting nextSessionMeeting = nextSessionMeetingRepository.findFirst().get();
				if (nextSessionMeeting.getNextMeeting().isBefore(LocalDateTime.now(ZoneId.of("Europe/Rome")))) {
					result = "Prossima sessione: non settata";
				} else {
					result = formatterOut.format(nextSessionMeeting.getNextMeeting());
				}
			} catch (Exception e) {
				log.error(e.toString());
				result = "Prossima sessione: non settata";
			}
			break;
		default:
			log.info("ECHO SERVICE");
			result = text;
			break;
		}
		return result;
	}

	private boolean hasJoinedGroup(Message message) {
		if (message.getNew_chat_members() == null)
			return false;
		for (User u : message.getNew_chat_members()) {
			if (u.getUsername().equals(USERNAME))
				return true;
		}
		return false;
	}

	private String createErrorResponse(String command, String[] params) {
		StringBuilder sb = new StringBuilder("Usage: ");
		sb.append(command);
		for (String param : params) {
			sb.append(" " + param);
		}
		return sb.toString();
	}

	private boolean isBlank(String s) {
		return s == null || s.isBlank();
	}
}
