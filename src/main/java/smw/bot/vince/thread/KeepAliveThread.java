package smw.bot.vince.thread;

public class KeepAliveThread implements Runnable {

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