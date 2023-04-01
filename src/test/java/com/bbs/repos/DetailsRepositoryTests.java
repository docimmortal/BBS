package com.bbs.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
public class DetailsRepositoryTests {

	@Autowired
	private DetailsRepository repo;
	
	@Test
	public void testFindAll() {
		List<Details> details = repo.findAll();
		assertEquals(2, details.size());
	}
	
	@Test
	public void testFindByUsername() {
		Optional<Details> optional = repo.findOptionalByUsername("bob");
		assertTrue(optional.isPresent());
	}
}
