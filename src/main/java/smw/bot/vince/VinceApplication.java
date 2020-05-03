package smw.bot.vince;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VinceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinceApplication.class, args);
	}

	private class KeepAliveThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1500000);
					System.out.println("SVEGLIA!");
				} catch (InterruptedException e) {
					continue;
				}
			}
		}

	}

}
