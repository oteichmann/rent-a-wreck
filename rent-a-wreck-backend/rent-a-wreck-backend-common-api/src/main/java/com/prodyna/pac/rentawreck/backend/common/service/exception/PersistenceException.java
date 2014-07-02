/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common.service.exception;

import javax.ejb.ApplicationException;


/**
 * PersistenceException
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
@ApplicationException
public class PersistenceException extends RuntimeException {

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
	public PersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public PersistenceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PersistenceException(Throwable cause) {
		super(cause);
	}
}
