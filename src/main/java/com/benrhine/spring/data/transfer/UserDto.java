package com.benrhine.spring.data.transfer;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@NotEmpty
	@Size(max = 30)
	private String firstName;
	
	@NotNull
	@NotEmpty
	@Size(max = 30)
	private String lastName;
	
	@NotNull
	@NotEmpty
	@Email
	@Size(max = 50)
	private String userName;
	
	@NotNull
	@NotEmpty
	@Email
	@Size(max = 50)
	private String reUserName;
	
	@NotNull
	@NotEmpty
	@Size(max = 255)
	private String userPass;
	
	@NotNull
	@NotEmpty
	@Size(max = 255)
	private String reUserPass;
	
	private Integer enabled;
	private Long role;
	
	public Long getId() {
		return id;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(final String userName) {
		this.userName = userName;
	}
	public String getReUserName() {
		return reUserName;
	}
	public void setReUserName(final String reUserName) {
		this.reUserName = reUserName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(final String userPass) {
		this.userPass = userPass;
	}
	public String getReUserPass() {
		return reUserPass;
	}
	public void setReUserPass(final String reUserPass) {
		this.reUserPass = reUserPass;
	}
	public Integer getEnabled() {
		return this.enabled;
	}
	public void setEnabled(final Integer enabled) {
		this.enabled = enabled;
	}
	public Long getRole() {
		return role;
	}
	public void setRole(final Long role) {
		this.role = role;
	}
}
