//package com.benrhine.spring.practice;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//import com.benrhine.spring.domain.Domain;
//
//public abstract class BaseDomain implements Domain {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "ID", nullable = false)
//	private Long id;
//	
//	public Long getId() {
//		return this.id;
//	}
//	
//	public void setId(final Long id) {
//		this.id = id;
//	}
//
//}
