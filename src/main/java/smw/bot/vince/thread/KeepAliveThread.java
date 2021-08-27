package smw.bot.vince.thread;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.quarkus.runtime.Startup;
import smw.bot.vince.client.SelfRestClient;

@Startup
public class KeepAliveThread extends Thread {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	private final SelfRestClient restClient;
	
	@Autowired
	public KeepAliveThread(@RestClient SelfRestClient restClient) {
		this.restClient = restClient;
	}
	
	@Override
	public void run() {
		log.info(getClass().getSimpleName() + " started");
		while (true) {
			try {
				Thread.sleep(1710000); // 28,5 minuti
				log.info("SVEGLIA!");
				log.info(restClient.keepAlive());
			} catch (InterruptedException e) {
				continue;
			}
		}
	}

}