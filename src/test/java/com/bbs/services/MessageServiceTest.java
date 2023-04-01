package com.bbs.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bbs.entites.Details;
import com.bbs.entites.Message;
import com.bbs.entites.MessageForum;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class MessageServiceTest {

	@Autowired
	private MessageService service;
	
	@Autowired
	private DetailsService dService;
	
	@Autowired
	private MessageForumService mfService;
	
	private Details author;
	private MessageForum forum;
	
	@BeforeEach
	public void before() {
		author = new Details("amy","Amy","Jones","a.j@email.com");
		author=dService.save(author);
		forum = new MessageForum("Boring","Boring stuff");
		forum = mfService.save(forum);
	}
	
	@Test
	public void testSave() {
		Message message = new Message("The Title","This is a test", author, forum);
		message = service.save(message);
		assertNotNull(message.getId());
		MessageForum forum = message.getForum();
		assertNotNull(forum.getId());
		Details details = message.getAuthor();
		assertNotNull(details.getId());
	}
}
