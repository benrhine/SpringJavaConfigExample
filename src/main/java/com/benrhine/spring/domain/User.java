package com.benrhine.spring.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.benrhine.spring.util.RoleUtil;

@Entity
@Table(name="USER")
public class User implements Domain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@NotNull
	@NotEmpty
	@Size(max = 30)
	@Column(name = "FIRST_NAME", nullable = false, length = 30)
	private String firstName;
	
	@NotNull
	@NotEmpty
	@Size(max = 30)
	@Column(name = "LAST_NAME", nullable = false, length = 30)
	private String lastName;
	
	@NotNull
	@NotEmpty
	@Email
	@Size(max = 50)
	@Column(name = "USER_NAME", nullable = false, length = 50, unique=true)
	private String userName; //this is the email
	
	@NotNull
	@NotEmpty
	@Size(max = 255)
	@Column(name = "USER_PASS", nullable = false, length = 255)
	private String userPass;
	
	@NotNull
	@NotEmpty
	@Size(max = 40)
	@Column(name = "SHORT_USER_NAME", nullable = false, length = 40, unique=true)
	private String shortUsername;
	
	@Column(name = "ENABLED", nullable = false)
	private Integer enabled;
	
	// Maps to field user in Role domain object not table USER
	@OneToOne(mappedBy="user", cascade = {CascadeType.ALL})
	private Role role;
	
	public User() {
		// there has to be a better way to default this
		this.enabled = 1;
		this.role = (new Role(RoleUtil.ROLE_USER, this));
	}
	
	public User(final User user) {
		this.firstName 		= user.getFirstName();
		this.lastName 		= user.getLastName();
		this.userName 		= user.getUsername();
		this.userPass 		= user.getPassword();
		this.enabled 		= user.getEnabled();
		this.role 			= user.getRole();
		this.shortUsername 	= user.getShortUsername();
	}
	
	public User(final String firstName, final String lastName, final String userName, final String password, final Integer enabled, final String shortUsername, final Role role) {
		this.firstName 		= firstName;
		this.lastName 		= lastName;
		this.userName 		= userName;
		this.userPass 		= password;
		this.enabled 		= enabled;
		this.role 			= role;
		this.shortUsername 	= shortUsername;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return this.userName;
	}
	
	public void setUsername(final String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return this.userPass;
	}
	
	public void setPassword(final String userPass) {
		this.userPass = userPass;
	}
	
	public Integer getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(final Integer enabled) {
		this.enabled = enabled;
	}
	
	public String getShortUsername() {
		return this.shortUsername;
	}
	
	public void setShortUsername(final String shortUsername) {
		this.shortUsername = shortUsername;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public void setRole(final Role role) {
		this.role = role;
	}

	public Boolean compareTo(Object x, Object y) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Not sure if this works quite right with the role object
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;			
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User user = (User)obj;
		return  (this.firstName.equals(user.getFirstName()) &&
				(this.lastName.equals(user.getLastName())) &&
				(this.userName.equals(user.getUsername())) &&
				(this.userPass.equals(user.getPassword())) &&
				(this.shortUsername.equals(user.getShortUsername())) &&
				(this.enabled == user.getEnabled()) &&
				(this.role.getRole() == user.getRole().getRole()) &&
				(this.role.getUser().equals(this)));
	}

	public Object copy(Object x, Object y) {
		// TODO Auto-generated method stub
		return null;
	}
}
