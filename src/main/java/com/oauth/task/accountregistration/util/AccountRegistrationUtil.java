/**
 * 
 */
package com.oauth.task.accountregistration.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.oauth.task.accountregistration.AccountRegistrationConstants;
import com.oauth.task.accountregistration.exception.AccountRegistrationException;

/**
 * Utility class to get the session info for the API request object
 * This also validates the response from the magento service call
 * @author SUMAN
 *
 */
@Component("accRegUtil")
@PropertySource("classpath:application.properties")
public class AccountRegistrationUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRegistrationUtil.class);

	@Value("${magento.server.hostname}")
    private static String magentoHostname;
	/**
	 * Get the session information required to register an account.
	 * @return
	 */
	public Map<String, String> getAccountRegistrationSessionInfo() {
		
		Map<String, String> sessionInfo = new HashMap<>();
		
		URLConnection connection;
		try {
			// Open URLConnection to the host to get the registration page
			String uri = "http://54.144.243.81/index.php/customer/account/create/";
			connection = new URL(uri).openConnection();
			LOGGER.info("Connection established with 54.144.243.81");
			
			// get the Cookie information from the header
			List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
			String cookieFrontEnd = null;
			for (String cookieName : cookies) {
				if (cookieName.startsWith(AccountRegistrationConstants.COOKIE_NAME_FRONTEND)) {
					cookieFrontEnd = cookieName.substring(cookieName.indexOf(AccountRegistrationConstants.COOKIE_NAME_FRONTEND), cookieName.indexOf(";"));
					LOGGER.info("Found Cookie with name frontend");
					break;
				}
			}
			
			final InputStream inputStream = connection.getInputStream();
	        final String html = IOUtils.toString(inputStream);
	        Document htmlDoc = Jsoup.parse(html);
			Elements formKey = htmlDoc.select("input[name=form_key]");
			String formKeyValue = formKey.get(0).attr("value");
	        IOUtils.closeQuietly(inputStream);
	        
	        sessionInfo.put(AccountRegistrationConstants.FORM_KEY, formKeyValue);
			sessionInfo.put(AccountRegistrationConstants.COOKIE_VALUE, cookieFrontEnd);
			LOGGER.info("form_Key: "+formKeyValue+", cookieValue: "+cookieFrontEnd);
			
		} catch (IOException e) {
			throw new AccountRegistrationException("Error getting session info. "+e);
		}
		
		return sessionInfo;
	}
	
	/**
	 * Validate the response from the POST method invocation.
	 * @param response
	 * @param sessionInfo
	 * @return
	 */
	public String validateResponse(ResponseEntity<String> response, Map<String, String> sessionInfo) {
		
			URI locationUrl = response.getHeaders().getLocation();
			String errorMessage = null;
			
			if (locationUrl != null && locationUrl.toString().contains("create")) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.TEXT_HTML);
				headers.add("Cookie", sessionInfo.get(AccountRegistrationConstants.COOKIE_VALUE));
				
				UriComponentsBuilder builder = UriComponentsBuilder
					    .fromUriString(locationUrl.toString());
					    // Add query parameter
					   // .queryParam(AccountRegistrationConstants.FORM_KEY, sessionInfo.get(AccountRegistrationConstants.FORM_KEY));// This is not needed to make a get call
	
				RestTemplate restTemplate = new RestTemplate();
				HttpEntity<?> entity = new HttpEntity<>(headers);
				HttpEntity<String> responseData = restTemplate.exchange(
				        builder.build().encode().toUri(), 
				        HttpMethod.GET, 
				        entity, 
				        String.class);
				System.out.println(responseData.getBody());
				Document htmlDoc = Jsoup.parse(responseData.getBody());
				Elements li = htmlDoc.select("li.error-msg");
				Elements ul = li.select("ul");
				errorMessage = ul.select("li").select("span").text();
			}
			
			return errorMessage;
	}
}
