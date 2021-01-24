package smw.bot.vince.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import smw.bot.vince.model.db.NextSessionMeeting;
import smw.bot.vince.model.tg.Message;
import smw.bot.vince.model.tg.Update;
import smw.bot.vince.model.tg.User;
import smw.bot.vince.repository.NextSessionMeetingRepository;
import smw.bot.vince.service.VinceService;

@Service
public class VinceServiceImpl implements VinceService {

	private final Logger log;
	private final DateTimeFormatter formatterIn;
	private final DateTimeFormatter formatterOut;
	private final RestTemplate restTemplate;
	private final NextSessionMeetingRepository nextSessionMeetingRepository;

	private final static String USERNAME = "VinceEarlLiamBot";
	private final static String URL = "https://api.telegram.org/bot1001648084:AAHPcuvoo8gN-XAskF3jtsbBTRTQB2GP3x8/sendMessage";
	private final static String HELLO_MESSAGE = "Signore e signori, sono qui per divertire!";
	private final static String DATE_TIME_FORMAT_IN = "dd/MM/yyyy HH:mm";
	private final static String DATE_TIME_FORMAT_OUT = "'Prossima sessione:' EEEE d MMMM 'ore' HH:mm";
	private final static String SET_SESSIONE_COMMAND = "/set_sessione";
	private final static String GET_SESSIONE_COMMAND = "/sessione";

	@Autowired
	public VinceServiceImpl(RestTemplate restTemplate, NextSessionMeetingRepository nextSessionMeetingRepository) {
		this.restTemplate = restTemplate;
		this.nextSessionMeetingRepository = nextSessionMeetingRepository;
		this.log = LoggerFactory.getLogger(this.getClass());
		this.formatterIn = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_IN, Locale.ITALIAN);
		this.formatterOut = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_OUT, Locale.ITALIAN);
	}

	@Override
	public ResponseEntity<String> acceptUpdate(Update update) {
		String text = update.getMessage().getText();
		String responseText = null;
		log.info("TEXT: {}", text);
		if (Strings.isBlank(text) && hasJoinedGroup(update.getMessage())) {
			log.info("JOINED_GROUP SERVICE");
			responseText = HELLO_MESSAGE;
		} else {
			responseText = parseCommand(text);
		}
		if (!Strings.isBlank(responseText))
			return reply(update.getMessage().getChat().getId(), responseText);
		return ResponseEntity.ok(null);
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

	private ResponseEntity<String> reply(Long chatId, String text) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		String uri = buildUri(chatId, text);
		return restTemplate.postForEntity(uri, headers, String.class);
	}

	private String buildUri(Long chatId, String text) {
		StringBuilder sb = new StringBuilder(URL).append("?").append("chat_id=").append(chatId).append("&")
				.append("text=").append(text);
		return sb.toString();
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

}
