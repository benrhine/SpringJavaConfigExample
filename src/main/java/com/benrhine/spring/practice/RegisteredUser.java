//package com.benrhine.spring.practice;
//package com.benrhine.spring.domain;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//import org.hibernate.validator.constraints.NotEmpty;
//
//@Entity
//@Table(name="REGISTERED_USER")
//public class RegisteredUser extends User {
//	
//	@NotNull
//	@NotEmpty
//	@Size(max = 10)
//	@Column(name = "SHORT_USER_NAME", nullable = false, length = 10, unique=true)
//	private String shortUsername;
//	
//	// Maps to field user in Role domain object not table USER
////	@OneToOne(mappedBy="registeredUser", cascade = {CascadeType.ALL})
////	private Role role;
//	
//	public RegisteredUser() {
//		/* Leave blank */
//	}
//	
//	public RegisteredUser(RegisteredUser registeredUser) {
//		super.setFirstName(registeredUser.getFirstName());
//		super.setLastName(registeredUser.getLastName());
//		super.setUsername(registeredUser.getUsername());
//		super.setPassword(registeredUser.getPassword());
//		super.setEnabled(registeredUser.getEnabled());
//		this.shortUsername = registeredUser.getShortUsername();
//		this.role = registeredUser.getRole();
//	}
//	
//	public RegisteredUser(final String shortUsername, final Role role) {
//		this.shortUsername = shortUsername;
//		this.role = role;
//	}
//	
//	public RegisteredUser(final String firstName, final String lastName, final String userName, final String password, final Integer enabled, final String shortUsername, final Role role) {
//		super.setFirstName(firstName);
//		super.setLastName(lastName);
//		super.setUsername(userName);
//		super.setPassword(password);
//		super.setEnabled(enabled);
//		this.shortUsername = shortUsername;
//		this.role = role;
//	}
//	
//	public String getShortUsername() {
//		return this.shortUsername;
//	}
//	
//	public void setShortUsername(final String shortUsername) {
//		this.shortUsername = shortUsername;
//	}
//	
//	public Role getRole() {
//		return this.role;
//	}
//	
//	public void setRole(final Role role) {
//		this.role = role;
//	}
//
//	public Boolean compareTo(Object x, Object y) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Boolean equals(Object x, Object y) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Object copy(Object x, Object y) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
