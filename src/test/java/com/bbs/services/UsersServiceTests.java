package com.bbs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bbs.entites.User;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class UsersServiceTests {

	@Autowired
	private UsersService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void testSave() {
		User user = new User("fred", passwordEncoder.encode("Pass123"), true);
		user=service.save(user);
		assertEquals(3, user.getId());
	}
}
