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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bbs.entites.UserDetails;
import com.bbs.entites.Message;
import com.bbs.entites.MessageForum;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MessageServiceTest {

	@Autowired
	private MessageService service;
	
	@Autowired
	private DetailsService dService;
	
	@Autowired
	private MessageForumService mfService;
	
	private UserDetails author;
	private MessageForum forum;
	
	@BeforeEach
	public void before() {
		author = new UserDetails("amy","Amy","Jones","a.j@email.com");
		author=dService.save(author);
		forum = new MessageForum("Boring","Boring stuff");
		forum = mfService.save(forum);
	}
	
	@Test
	@Transactional
	public void testSave() {
		Message message = new Message("The Title","This is a test", author, forum);
		message = service.save(message);
		assertNotNull(message.getId());
		System.out.println("MID: "+message.getId());
		MessageForum forum = message.getMessageForum();
		assertNotNull(forum.getId());
		System.out.println("MFID: "+forum.getId());
		UserDetails details = message.getUserDetails();
		assertNotNull(details.getId());
		System.out.println("DID: "+details.getId());
	}
	
	@Test
	@Transactional
	// Not sure why this fails
	public void testSaveToAutopopulatedData() {
		Optional<UserDetails> dOptional = dService.findOptionalByUsername("bob");
		assertTrue(dOptional.isPresent());
		UserDetails author1 = dOptional.get();
		
		Optional<Message> optional = service.findById(1);
		assertTrue(optional.isEmpty());
		
		
		Optional<MessageForum> mfOptional = mfService.findById(1);
		assertTrue(mfOptional.isPresent());
		MessageForum forum1 = mfOptional.get();
		Message message = new Message("The Title","This is a test", author1, forum1);
		message = service.save(message);
		assertNotNull(message.getId());
		assertEquals(3, message.getId());
		UserDetails details = message.getUserDetails();
		assertEquals(1, details.getId());
		MessageForum mForum = message.getMessageForum();
		assertEquals(1, mForum.getId());
		
		// Refetch
		Optional<Message> optional3 = service.findById(3);
		assertTrue(optional3.isPresent());
		message = optional3.get();
		assertEquals(3, message.getId());
		details = message.getUserDetails();
		assertNotNull(details);
		MessageForum forum = message.getMessageForum();
		assertNotNull(forum);
	}

	@Test
	@Transactional
	public void testFindById() {
		Optional<Message> optional = service.findById(1);
		assertTrue(optional.isPresent());
		Message message = optional.get();
		assertEquals(1, message.getId());
		assertEquals("Hello!",message.getTitle());
		assertEquals("This is a test message.", message.getMessage());
		// I guess there is a database issue because testSaveToAutopopulatedData works.
		UserDetails detail = message.getUserDetails();
		assertNotNull(detail);
		MessageForum forum = message.getMessageForum();
		assertNotNull(forum);
	}
}
