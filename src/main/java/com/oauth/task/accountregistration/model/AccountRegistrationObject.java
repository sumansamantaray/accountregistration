/**
 * 
 */
package com.oauth.task.accountregistration.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 * This POJO contains the request object for the rest API
 * @author SUMAN
 *
 */
public class AccountRegistrationObject {

	@NotNull
	private String firstName;
	
	private String middleName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	@Email
	private String emailAddress;
	
	@NotNull
	@Size(min=6)
	private String password;
	
	@NotNull
	@Size(min=6)
	private String confirmPassword;
	
	private String accountRegistrationStatus;
	
	
	public AccountRegistrationObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountRegistrationObject(String firstName, String middleName,
			String lastName, String emailAddress, String password,
			String confirmPassword) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the accountRegistrationStatus
	 */
	public String getAccountRegistrationStatus() {
		return accountRegistrationStatus;
	}

	/**
	 * @param accountRegistrationStatus the accountRegistrationStatus to set
	 */
	public void setAccountRegistrationStatus(String accountRegistrationStatus) {
		this.accountRegistrationStatus = accountRegistrationStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccountRegistrationObject [firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", emailAddress=" + emailAddress + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	
	
}
