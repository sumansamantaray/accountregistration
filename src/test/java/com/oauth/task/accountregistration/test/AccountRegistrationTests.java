/**
 * 
 */
package com.oauth.task.accountregistration.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth.task.accountregistration.AccountRegistrationConstants;
import com.oauth.task.accountregistration.controller.AccountRegistrationController;
import com.oauth.task.accountregistration.model.AccountRegistrationObject;
import com.oauth.task.accountregistration.util.AccountRegistrationUtil;

/**
 * @author SUMAN
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRegistrationTests {
	
	//@Autowired
	private MockMvc mockMvc;
	
	AccountRegistrationUtil accRegUtil;
	
	AccountRegistrationObject accountInfo = new AccountRegistrationObject("test1", "", "test1", "test1@email.com", "test12", "test12");
	
	JacksonTester<AccountRegistrationObject> jsonTester;
	@InjectMocks
	AccountRegistrationController accRegController;
	
	 @Before
     public void setup() {
         ObjectMapper objectMapper = new ObjectMapper();
         JacksonTester.initFields(this, objectMapper);
         accRegController = new AccountRegistrationController();
         mockMvc = MockMvcBuilders.standaloneSetup(accRegController).build();
         accRegUtil = new AccountRegistrationUtil();
     }
	
	@Test
	public void registerAccountTest() throws Exception {
		Map<String, String> sessionInfo = accRegUtil.getAccountRegistrationSessionInfo();
		AccountRegistrationUtil mock = Mockito.mock(AccountRegistrationUtil.class);
		Mockito.when(mock.getAccountRegistrationSessionInfo()).thenReturn(sessionInfo);
		/*String cookieValue = sessionInfo.get(AccountRegistrationConstants.COOKIE_VALUE);
		String[] cookieNameVal = cookieValue.split("=");
		Cookie sessionCookie = new Cookie(cookieNameVal[0], cookieNameVal[1]);*/
		final String inputObjToJson = jsonTester.write(accountInfo).getJson();
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/registerAccount")
				.accept(MediaType.APPLICATION_JSON).content(inputObjToJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	
	/*public void createStudentCourse() throws Exception {
		Course mockCourse = new Course("1", "Smallest Number", "1",
				Arrays.asList("1", "2", "3", "4"));

		// studentService.addCourse to respond back with mockCourse
		Mockito.when(
				studentService.addCourse(Mockito.anyString(),
						Mockito.any(Course.class))).thenReturn(mockCourse);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/students/Student1/courses")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/students/Student1/courses/1",
				response.getHeader(HttpHeaders.LOCATION));

	}*/
}
