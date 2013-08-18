package com.benrhine.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ROLE")
public class Role implements Domain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "ROLE")
	private Long role;
	
	@OneToOne
	private User user;
	
	public Role() {
		/* Leave blank */
	}
	
	public Role(final Role role) {
		this.role = role.getRole();
		this.user = role.getUser();
	}
	
	public Role(final Long role, final User u) {
		this.role = role;
		this.user = u;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	public Long getRole() {
		return this.role;
	}
	
	public void setRole(final Long role) {
		this.role = role;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(final User user) {
		this.user = user;
	}

	public Boolean compareTo(Object x, Object y) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(Object x) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object copy(Object x, Object y) {
		// TODO Auto-generated method stub
		return null;
	}

}
