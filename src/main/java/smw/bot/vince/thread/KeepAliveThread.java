package smw.bot.vince.thread;

import org.springframework.web.client.RestTemplate;

public class KeepAliveThread extends Thread {
	
	private final RestTemplate restTemplate;
	private final static String URL = "https://master-manager.herokuapp.com/wake-up";
	
	public KeepAliveThread() {
		this.restTemplate = new RestTemplate();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1740000); // 29 minuti
				System.out.println("SVEGLIA!");
				System.out.println(restTemplate.getForObject(URL, String.class));
			} catch (InterruptedException e) {
				continue;
			}
		}
	}

}