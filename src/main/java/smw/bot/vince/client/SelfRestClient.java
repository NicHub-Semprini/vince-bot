package smw.bot.vince.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "self-rest-client")
public interface SelfRestClient {
	
	@GET
	@Path("/wake-up")
	public String keepAlive();

}
