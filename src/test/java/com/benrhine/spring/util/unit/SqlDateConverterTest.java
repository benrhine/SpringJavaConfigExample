package com.benrhine.spring.util.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.benrhine.spring.util.SqlDateConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SqlDateConverterTest {
	private SimpleDateFormat dateFormat;
	private SqlDateConverter converter;
	
	@Before
	public void setUp() throws Exception {
		final String format = "dd/MM/yyyy";
		dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		
		converter = new SqlDateConverter(dateFormat, false, 10);
	}
	
	@Test
	public void instantiateSqlDateConverterWithTwoArgConstructor() {
		assertNotNull(new SqlDateConverter(dateFormat, false));
	}
	
	@Test
	public void instantiateSqlDateConverterWithThreeArgConstructor() {
		assertNotNull(new SqlDateConverter(dateFormat, false, 10));
	}
	
	@Test
	public void returnsDateAsTextWhenValueIsNull() {
		String date = converter.getAsText();
		
		assertEquals(date, "");
	}
	
	@Test
	public void returnsDateAsTextWhenValueIsNotNull() {
		converter.setValue(new Date(0));
		String date = converter.getAsText();
		
		assertEquals(date, "31/12/1969");
	}

}
