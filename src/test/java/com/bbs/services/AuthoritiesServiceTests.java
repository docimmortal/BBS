package com.bbs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bbs.entites.Authority;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class AuthoritiesServiceTests {

	@Autowired
	private AuthoritiesService service;
	
	@Test
	public void testSave() {
		Authority authority = new Authority("fred", "ROLE_USER");
		authority=service.save(authority);
		assertEquals(3, authority.getId());
	}
}
