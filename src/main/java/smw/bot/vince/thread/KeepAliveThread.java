package smw.bot.vince.thread;

import org.springframework.web.client.RestTemplate;

public class KeepAliveThread extends Thread {
	
	private final RestTemplate restTemplate;
	private final static String url = "https://master-manager.herokuapp.com/hello";
	
	public KeepAliveThread() {
		this.restTemplate = new RestTemplate();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1500000); // 25 minuti
				System.out.println("SVEGLIA!");
				System.out.println(restTemplate.getForObject(url, String.class));
			} catch (InterruptedException e) {
				continue;
			}
		}
	}

}