package smw.bot.vince.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class KeepAliveThread extends Thread {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	private final RestTemplate restTemplate;
	private final static String URL = "https://master-manager.herokuapp.com/wake-up";
	
	public KeepAliveThread() {
		this.restTemplate = new RestTemplate();
	}

	@Override
	public void run() {
		log.info(getClass().getSimpleName() + " started");
		while (true) {
			try {
				Thread.sleep(1740000); // 29 minuti
				log.info("SVEGLIA!");
				log.info(restTemplate.getForObject(URL, String.class));
			} catch (InterruptedException e) {
				continue;
			}
		}
	}

}