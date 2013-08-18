package com.benrhine.spring.util;

import org.apache.log4j.Logger;

import com.benrhine.spring.data.transfer.UserDto;
import com.benrhine.spring.domain.User;

public class LogUtil {
	
	public static void logUserObject(Logger logger, final User user) {
		logger.info("Id: " 			+ user.getId());
		logger.info("First Name: " 	+ user.getFirstName());
		logger.info("Last Name: " 	+ user.getLastName());
		logger.info("User Name: " 	+ user.getUsername());
		logger.info("Short Name:" 	+ user.getShortUsername());
		logger.info("Password: " 	+ user.getPassword());
		logger.info("Role: " 		+ user.getRole());
		logger.info("Enabled: " 	+ user.getEnabled());
	}
	
	public static void logUserDtoObject(Logger logger, final UserDto userDto) {
		logger.info("Id: " 			+ userDto.getId());
		logger.info("First Name: " 	+ userDto.getFirstName());
		logger.info("Last Name: " 	+ userDto.getLastName());
		logger.info("User Name 1: " + userDto.getUserName());
		logger.info("User Name 2: " + userDto.getReUserName());
		logger.info("Password 1: "	+ userDto.getUserPass());
		logger.info("Password 2: " 	+ userDto.getReUserPass());
	}
	
//	public static void logRoleObject(Logger logger, final Role role) {
//		logger.info("Id: " 		+ role.getId());
//		logger.info("Role: " 	+ role.getRole());
//		logger.info("User Id: "	+ role.getUser().getId());
//	}
}
