package com.bbs.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bbs.entites.Details;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class DetailsServiceImplTests {

	@Autowired
	private DetailsService service;
	
	@Test
	public void testfindByUsername() {
		Optional<Details> optional = service.findOptionalByUsername("bob");
		assertTrue(optional.isPresent());
	}
	
	@Test
	public void testSaveNewDetails() {
		Details details = new Details("amy","Amy","Jones","a.j@email.com");
		details = service.save(details);
		assertNotNull(details.getId());
	}
}
