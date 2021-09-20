package smw.bot.vince;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.inject.Inject;

import com.smw.crypto.aes.AESUtils;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import smw.bot.vince.thread.KeepAliveThread;

@QuarkusMain
public class Main {

	public static void main(String ... args) {
        Quarkus.run(App.class, args);
        try {
	        SecretKey key = AESUtils.generateKey(AESUtils.KEY_LENGTH_256);
	        IvParameterSpec iv = AESUtils.generateIv();
	        String algorithm = "AES/OFB32/PKCS5Padding";
	        String plain = "falco";
	        System.out.println("plain = " + plain);
	        String crypted = AESUtils.encryptToString(algorithm, key, iv, plain);
	        System.out.println("crypted = " + crypted);
	        String decrypted = AESUtils.decryptToString(algorithm, key, iv, crypted);
	        System.out.println("decrypted = " + decrypted);
	        System.out.println("equals = " + plain.equals(decrypted));
        } catch(Exception e) {
        	System.out.println("ECCEZIONE");
        	e.printStackTrace();
        }
    }
	
	public static class App implements QuarkusApplication {

		@Inject
		KeepAliveThread keepAliveThread;
		
        @Override
        public int run(String... args) throws Exception {
        	keepAliveThread.start();
            Quarkus.waitForExit();
            return 0;
        }
    }
}
