package smw.bot.vince;

import javax.inject.Inject;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import smw.bot.vince.thread.KeepAliveThread;

@QuarkusMain
public class Main {

	public static void main(String ... args) {
		System.out.println("SI VOLA");
        Quarkus.run(App.class, args);
//        Quarkus.run(args);
    }
	
	public static class App implements QuarkusApplication {

		@Inject
		private KeepAliveThread t;
		
        @Override
        public int run(String... args) throws Exception {
        	System.out.println("Partenza");
        	t.start();
            Quarkus.waitForExit();
            return 0;
        }
    }
}
