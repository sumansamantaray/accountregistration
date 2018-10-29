/**
 * 
 */
package com.oauth.task.accountregistration.exception;

import java.util.List;

/**
 * Exception class to capture the data validation exception
 * @author SUMAN
 *
 */
public final class DataFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataFormatException() {
        super();
    }

    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFormatException(String message) {
        super(message);
    }
    
    public DataFormatException(List<String> errorMessages) {
        super(errorMessages.toString());
    }

    public DataFormatException(Throwable cause) {
        super(cause);
    }
}
