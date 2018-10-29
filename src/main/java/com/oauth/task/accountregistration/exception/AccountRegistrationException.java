/**
 * 
 */
package com.oauth.task.accountregistration.exception;

import java.util.List;

/**
 * Exception class to capture the application level exceptions
 * @author SUMAN
 *
 */
public class AccountRegistrationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountRegistrationException() {
        super();
    }

    public AccountRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountRegistrationException(String message) {
        super(message);
    }
    
    public AccountRegistrationException(List<String> errorMessages) {
        super(errorMessages.toString());
    }

    public AccountRegistrationException(Throwable cause) {
        super(cause);
    }

}
