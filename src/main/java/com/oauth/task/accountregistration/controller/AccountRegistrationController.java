/**
 * 
 */
package com.oauth.task.accountregistration.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.oauth.task.accountregistration.AccountRegistrationConstants;
import com.oauth.task.accountregistration.exception.AccountRegistrationException;
import com.oauth.task.accountregistration.exception.DataFormatException;
import com.oauth.task.accountregistration.model.AccountRegistrationObject;
import com.oauth.task.accountregistration.util.AccountRegistrationUtil;

/**
 * This class exposes the rest API which intern invokes the Magento register account service
 * @author SUMAN
 *
 */
@RestController
public class AccountRegistrationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRegistrationController.class);
	
	@Autowired
	AccountRegistrationUtil accRegUtil;
	
	@PostMapping(path = "/registerAccount", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> registerAccount(@Validated @RequestBody AccountRegistrationObject accountInfo, Errors errors) {
		System.out.println("Inside registerAccount application-json");
		// In case of any input request validation errors, combine the error fields and message and throw DataFormatException
		if (errors.hasErrors()) {
			List<String> errorMsgs = new ArrayList<>();
			for (FieldError fieldError : errors.getFieldErrors()) {
				String errorMessage = fieldError.getField()+" "+fieldError.getDefaultMessage();
				errorMsgs.add(errorMessage);
			}
			throw new DataFormatException(errorMsgs);
		}
		
		// Get the session information to communicate with the server
		// The request is identified with cookie and form_key value
		Map<String, String> sessionInfo = accRegUtil.getAccountRegistrationSessionInfo();	
		LOGGER.info("Returned session info from Util class");
		
		// Set the cookie header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Cookie", sessionInfo.get(AccountRegistrationConstants.COOKIE_VALUE));

		// Set the input data to the post request
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add(AccountRegistrationConstants.FORM_KEY, sessionInfo.get(AccountRegistrationConstants.FORM_KEY));
		map.add("firstname", accountInfo.getFirstName());
		map.add("middlename", accountInfo.getMiddleName());
		map.add("lastname", accountInfo.getLastName());
		map.add("email", accountInfo.getEmailAddress());
		map.add("password", accountInfo.getPassword());
		map.add("confirmation", accountInfo.getConfirmPassword());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		LOGGER.info("HttpEntity is set.");
		
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.postForEntity( "http://54.144.243.81/index.php/customer/account/createpost/", request , String.class );
		LOGGER.info("POST method to register account is invoked.");
		
		// Throw AccountRegistrationException if the account is not registered successfully
		String errorMessage = accRegUtil.validateResponse(response, sessionInfo);
		if (errorMessage != null) {
			LOGGER.info("Account registration is NOT successful. "+errorMessage);
			throw new AccountRegistrationException(errorMessage);
		}
		LOGGER.info("Account registration is successful. ");
		accountInfo.setAccountRegistrationStatus("SUCCESS");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String accountRegistrationResponse = null;
		try {
			accountRegistrationResponse= ow.writeValueAsString(accountInfo);
		} catch (JsonProcessingException e) {
			// In case of exception, set the response string so that it would be visible to client in raw format.
			accountRegistrationResponse = "Account registration is successful.";
		} 
		// Return the method with cache control like below
		// return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS)).body(accountRegistrationResponse);
		return new ResponseEntity<Object>(accountRegistrationResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(path ="/registerAccount", consumes="text/plain", produces="text/plain" )
	public ResponseEntity<?> registerAccount(@Validated @RequestBody String name, Errors errors) {
		System.out.println("Inside registerAccount text-plain "+name);
		return null;
		
	}
	
}
