import java.util.UUID;

import org.jboss.security.auth.spi.Util;
import org.junit.Before;
import org.junit.Test;

import com.prodyna.pac.rentawreck.backend.common.model.User;

public class UserGeneratorTest {

	private User user = new User();

	@Before
	public void prepareUser() {
		this.user.setUuid(UUID.randomUUID().toString());
		this.user.setUsername(System.getProperty("ugt_username", "admin"));
		this.user.setPassword(System.getProperty("ugt_password", "admin"));
		this.user.setEmail(System.getProperty("ugt_email", "admin@rent-a-wreck.com"));
		this.user.setFirstName(System.getProperty("ugt_fistName", "Rent-A-Wreck"));
		this.user.setLastName(System.getProperty("ugt_lastName", "Administrator"));
	}

	@Test
	public void createDigestCredentials() {

//		String A1 = user.getUsername() + ":" + "rent-a-wreck" + ":" + user.getPassword();
//		String enryptedPassword = Util.createPasswordHash("MD5",
//				Util.RFC2617_ENCODING, null, null, A1);
		String enryptedPassword = Util.createPasswordHash("MD5",
				Util.RFC2617_ENCODING, null, null, user.getPassword());

		String sql = String
				.format("INSERT INTO raw_users (uuid, email, first_name, last_name, password, username)"
						+ " VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
						user.getUuid(), user.getEmail(), user.getFirstName(),
						user.getLastName(), enryptedPassword, user.getUsername());
		System.out.println(sql);
	}

}
