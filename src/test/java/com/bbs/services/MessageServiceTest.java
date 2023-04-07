package com.bbs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

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
		assertNotNull(message.getMid());
		System.out.println("MID: "+message.getMid());
		MessageForum forum = message.getForum();
		assertNotNull(forum.getMfid());
		System.out.println("MFID: "+forum.getMfid());
		Details details = message.getAuthor();
		assertNotNull(details.getDid());
		System.out.println("DID: "+details.getDid());
	}
	
	@Test
	public void testSaveToAutopopulatedData() {
		Optional<Details> dOptional = dService.findOptionalByUsername("bob");
		assertTrue(dOptional.isPresent());
		Details author1 = dOptional.get();
		Optional<MessageForum> mfOptional = mfService.findById(1);
		assertTrue(mfOptional.isPresent());
		MessageForum forum1 = mfOptional.get();
		Message message = new Message("The Title","This is a test", author1, forum1);
		message = service.save(message);
		assertNotNull(message.getMid());
		assertEquals(3, message.getMid());
		Details details = message.getAuthor();
		assertEquals(1, details.getDid());
		MessageForum mForum = message.getForum();
		assertEquals(1, mForum.getMfid());
		
		// Refetch
		Optional<Message> optional = service.findById(3);
		assertTrue(optional.isPresent());
		message = optional.get();
		assertEquals(3, message.getMid());
		details = message.getAuthor();
		assertNotNull(details);
		MessageForum forum = message.getForum();
		assertNotNull(forum);
	}

	@Test
	public void testFindById() {
		Optional<Message> optional = service.findById(1);
		assertTrue(optional.isPresent());
		Message message = optional.get();
		assertEquals(1, message.getMid());
		assertEquals("Hello!",message.getTitle());
		assertEquals("This is a test message.", message.getMessage());
		// I guess there is a database issue because testSaveToAutopopulatedData works.
		Details detail = message.getAuthor();
		assertNotNull(detail);
		MessageForum forum = message.getForum();
		assertNotNull(forum);
	}
}
