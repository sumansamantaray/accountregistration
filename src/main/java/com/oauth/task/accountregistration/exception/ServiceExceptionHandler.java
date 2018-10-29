/**
 * 
 */
package com.oauth.task.accountregistration.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oauth.task.accountregistration.model.RestErrorInfo;

/**
 * Custom exception handler to handle service exception
 * @author SUMAN
 *
 */
@RestControllerAdvice
public class ServiceExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

	    @ExceptionHandler(value = {DataFormatException.class})
	    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	    @ResponseBody
	    public ResponseEntity<Object> handleDataFormatException(DataFormatException ex) {
	        LOGGER.info("Converting Data Store exception to RestResponse : " + ex.getMessage());
	        RestErrorInfo errorInfo = new RestErrorInfo(ex, 400, HttpStatus.BAD_REQUEST);

	        return new ResponseEntity<Object>(errorInfo, HttpStatus.BAD_REQUEST);
	    }

	    @ResponseStatus(value = HttpStatus.NOT_FOUND)
	    @ExceptionHandler(AccountRegistrationException.class)
	    @ResponseBody
	    public ResponseEntity<Object> handleAccountRegistationException(AccountRegistrationException ex) {
	        LOGGER.info("AccountRegistrationException handler:" + ex.getMessage());
	        RestErrorInfo errorInfo = new RestErrorInfo(ex, 400, HttpStatus.BAD_REQUEST);
	        return new ResponseEntity<Object>(errorInfo, HttpStatus.BAD_REQUEST);
	    }


}
