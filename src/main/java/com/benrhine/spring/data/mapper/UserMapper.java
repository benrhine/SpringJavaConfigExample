package com.benrhine.spring.data.mapper;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.benrhine.spring.data.transfer.UserDto;
import com.benrhine.spring.domain.User;

@SuppressWarnings("deprecation")
public class UserMapper {
	//private static final Logger logger = Logger.getLogger(UserMapper.class);

	public static UserDto map(User user) {
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setUserName(user.getUsername());
			dto.setReUserName(user.getUsername());
			dto.setUserPass(user.getPassword());
			dto.setReUserPass(user.getPassword());
			dto.setRole(user.getRole().getRole());
			return dto;
	}
	
	public static User map(UserDto dto) {
		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setUsername(dto.getUserName());
		user.setShortUsername(makeShortName(dto.getUserName()));
		
		PasswordEncoder encoder = new Md5PasswordEncoder();
		user.setPassword(encoder.encodePassword(dto.getUserPass(), null));
		return user;
	}
	
	public static User mapClone(UserDto dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setUsername(dto.getUserName());
		user.setShortUsername(makeShortName(dto.getUserName()));
		user.setPassword(dto.getUserPass());
		
		return user;
	}
	
	/**
	 * Split the username (the email address) at the @ symbol. Take the first
	 * half of the split string.
	 * 
	 * @param username
	 * @return
	 */
	private static String makeShortName(final String username) {
		return username.split("@")[0];
	}
}