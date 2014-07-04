/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common.service.exception;

import javax.ejb.ApplicationException;


/**
 * Application runtime exception thrown on persistence validation errors.
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
@ApplicationException
public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2833282929420116745L;

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}
}
