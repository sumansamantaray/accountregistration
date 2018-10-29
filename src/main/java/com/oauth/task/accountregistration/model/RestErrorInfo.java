/**
 * 
 */
package com.oauth.task.accountregistration.model;

import org.springframework.http.HttpStatus;

/**
 * This class converts the runtime exception message to custom message 
 * which would be visible to the consumer of the register Account rest API 
 * @author SUMAN
 *
 */
public class RestErrorInfo {

	private final String detail;
    private final String message;
    private HttpStatus httpStatus;
    private int errorCode;
    
	public RestErrorInfo(Exception exp, int errorCode, HttpStatus httpStatus) {
		super();
		this.detail = exp.toString();
		this.message = exp.getLocalizedMessage();
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}

	/**
	 * @return the httpStatus
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RestErrorInfo [detail=" + detail + ", message=" + message
				+ ", httpStatus=" + httpStatus + ", errorCode=" + errorCode
				+ "]";
	}

   
}
