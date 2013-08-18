package com.benrhine.spring.domain;

public interface Domain {
	
	public Boolean compareTo(Object x, Object y);
	public boolean equals(Object obj);
	public Object copy(Object x, Object y);

}
