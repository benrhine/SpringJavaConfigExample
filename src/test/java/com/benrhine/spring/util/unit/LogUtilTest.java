package com.benrhine.spring.util.unit;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.benrhine.spring.data.mapper.UserMapper;
import com.benrhine.spring.data.transfer.UserDto;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.UserMaker;
import com.benrhine.spring.util.LogUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class LogUtilTest {
	private static final Logger logger = Logger.getLogger(LogUtilTest.class);
	private UserDto userDto;
	private User user;
	
	@Before
	public void setUp() throws Exception {
		user = UserMaker.makeUser("test@fake.com");
		userDto = UserMapper.map(user);
	}
	
	@Test
	public void logUtilUserDtoLogsObjectCorrectly() {
		try {
			LogUtil.logUserDtoObject(logger, userDto);
		} catch (Exception e) {
			fail("Should not have failed");
		}
	}
	
	@Test
	public void logUtilUserLogsObjectCorrectly() {
		try {
			LogUtil.logUserObject(logger, user);
		} catch (Exception e) {
			fail("Should not have failed");
		}
	}
}
