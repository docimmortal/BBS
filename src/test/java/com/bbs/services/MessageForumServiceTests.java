package com.bbs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bbs.entites.MessageForum;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class MessageForumServiceTests {

	@Autowired
	private MessageForumService service;
	
	@Test
	public void testFindAll() {
		List<MessageForum> forums = service.findAll();
		assertEquals(2, forums.size());
	}
}
