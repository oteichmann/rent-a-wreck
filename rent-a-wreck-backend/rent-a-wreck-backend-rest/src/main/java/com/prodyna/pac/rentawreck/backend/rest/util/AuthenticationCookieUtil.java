package com.prodyna.pac.rentawreck.backend.rest.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.core.NewCookie;

import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationServiceConstants;

/**
 * AuthenticationCookieUtil
 *
 * @author Oliver Teichmann
 *
 */
public class AuthenticationCookieUtil {
	

	/**
	 * Returns a valid cookie.
	 * @param token
	 * @return
	 */
	public static NewCookie createTokenCookie(String token) {
		Calendar cookieExpiryDate = new GregorianCalendar();
		cookieExpiryDate.add(Calendar.MINUTE, 30);
		NewCookie cookie = new NewCookie(AuthenticationServiceConstants.XSRF_TOKEN, token, "/" , "localhost", 1, 
				"RAW Session Token", 1800, cookieExpiryDate.getTime(), true, false);
		return cookie;
	}
	
	/**
	 * Returns an invalid cookie that will expire immediately.
	 * @return
	 */
	public static NewCookie createInvalidCookie() {
		NewCookie cookie = new NewCookie(AuthenticationServiceConstants.XSRF_TOKEN, "INVALID", "/" , "localhost", 1, 
				"RAW Session Token", 0, new Date(), true, false);
		
		return cookie;
	}

}
