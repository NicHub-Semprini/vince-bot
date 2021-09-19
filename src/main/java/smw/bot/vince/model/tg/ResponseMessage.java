package smw.bot.vince.model.tg;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseMessage {
	
	@JsonProperty("chat_id")
	private final long chatId;
	@JsonProperty("text")
	private final String text;
}
