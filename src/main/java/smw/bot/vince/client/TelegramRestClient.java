package smw.bot.vince.client;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import smw.bot.vince.model.tg.ResponseMessage;

@RegisterRestClient(configKey = "telegram-rest-client")
public interface TelegramRestClient {
	
	@POST
	@Path("/sendMessage")
	public Response sendMessage(ResponseMessage message);
}
