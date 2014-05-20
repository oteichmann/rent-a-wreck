import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.jboss.security.auth.callback.RFC2617Digest;
import org.junit.Assert;
import org.junit.Test;


public class UserCredentialsTest {

	
	@Test
	public void createDigestCredentials() {
		try {
			RFC2617Digest.main(new String [] {"admin","rent-a-wreck","admin"});
		} catch (NoSuchAlgorithmException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void generateUUID() {
		System.out.println(UUID.randomUUID().toString());
	}
}
