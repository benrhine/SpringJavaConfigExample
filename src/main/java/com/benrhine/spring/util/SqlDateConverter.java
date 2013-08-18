package com.benrhine.spring.util;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;

import org.springframework.util.StringUtils;

public class SqlDateConverter extends PropertyEditorSupport {
	private final DateFormat dateFormat;
	private final Boolean allowEmpty;
	private final Integer exactDateLength;
	
	public SqlDateConverter(final DateFormat dateFormat, final Boolean allowEmpty) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}
	
	public SqlDateConverter(final DateFormat dateFormat, final Boolean allowEmpty, final Integer exactDateLength) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}
	
	@Override
	public void setAsText(String str) throws IllegalArgumentException {
		long x = 0L;
		if (this.allowEmpty && !StringUtils.hasText(str)) {
			setValue(null);
		} else if ((str != null) && (this.exactDateLength >= 0) && (str.length() != this.exactDateLength)) {
			throw new IllegalArgumentException("Could not parse date: date length " + str.length() +" it is not exactly " + this.exactDateLength + " characters long");
		} else {
			try {
				str = str.replace('.',  '/');
				str = str.replace(',',  '/');
				str = str.replace('\\', '/');
				str = str.replace('-',  '/');
				str = str.replace(' ',  '/');
				
				java.util.Date date = dateFormat.parse(str);
				
				x = date.getTime();
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				
				setValue(sqlDate);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Could not parse date: " + x + " "+ e.getMessage());
			}
		}
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? this.dateFormat.format(value) : "");
	}
}
