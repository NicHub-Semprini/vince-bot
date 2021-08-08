package smw.bot.vince.client;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@RegisterRestClient(configKey = "telegram-rest-client")
public interface TelegramRestClient {
	
	@POST
	@Path("/sendMessage")
	public String sendMessage(@QueryParam("chat_id") Long chatId,  @QueryParam("text") String message);
}
