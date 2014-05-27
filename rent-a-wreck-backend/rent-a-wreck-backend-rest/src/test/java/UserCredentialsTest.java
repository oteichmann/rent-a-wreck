import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.jboss.security.auth.callback.RFC2617Digest;
import org.jboss.security.auth.spi.Util;
import org.junit.Assert;
import org.junit.Test;

public class UserCredentialsTest {

	
	@Test
	public void createDigestCredentials() {
		try {
			RFC2617Digest.main(new String [] {"admin","rent-a-wreck","adminpw"});
		} catch (NoSuchAlgorithmException e) {
			Assert.fail(e.getMessage());
		}
		
		String enryptedPassword = Util.createPasswordHash("MD5",
                Util.RFC2617_ENCODING,
                null,
                null,
                "adminpw");
//		String enryptedPassword = Util.encodeRFC2617("adminpw".getBytes());
		
		System.out.println(enryptedPassword);  
		
        String username = "admin";
        String realm = "rent-a-wreck";
        String password = "adminpw";
        String A1 = username + ":" + realm + ":" + password;
		enryptedPassword = Util.createPasswordHash("MD5",
                Util.RFC2617_ENCODING,
                null,
                null,
                A1);
        
		System.out.println(enryptedPassword);  
	}
	
	@Test
	public void generateUUID() {
		System.out.println(UUID.randomUUID().toString());
	}
}
