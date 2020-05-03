package smw.bot.vince;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import smw.bot.vince.thread.KeepAliveThread;

@SpringBootApplication
public class VinceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinceApplication.class, args);
		KeepAliveThread kat = new KeepAliveThread();
	    kat.start();
	}
	
}
