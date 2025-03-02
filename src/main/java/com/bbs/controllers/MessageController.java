package com.bbs.controllers;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bbs.entites.Message;
import com.bbs.services.LastReadMessageServiceImpl;
import com.bbs.services.MessageForumService;
import com.bbs.services.MessageService;
import com.bbs.utilities.MenuUtilities;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MessageController {

	@Autowired
	private MessageService mService;
	
	@Autowired
	private MessageForumService mfService;
	
	@Autowired
	private LastReadMessageServiceImpl lrmService;
	
	@PostMapping("/readMessage")
	public String readMessage(@RequestParam(required=true) String userDetailsId,
			@RequestParam(required = false, defaultValue="0") String messageId, 
			@RequestParam(required = false, defaultValue="") String messageForumId,
			@RequestParam(required = false, defaultValue="") String action,
			@RequestParam(required = false, defaultValue="") String nextForumId,
			@RequestParam(required = false, defaultValue="") String nextMessageId,
			@RequestParam(required = false, defaultValue="") String prevForumId,
			@RequestParam(required = false, defaultValue="") String prevMessageId,
			Model model, HttpServletRequest request) {
		
		// Cleansing data
		messageId=messageId.replaceAll("[^0-9]", "");
		messageForumId=messageForumId.replaceAll("[^0-9]", "");
		
		System.out.println("Got userDetailsId: "+userDetailsId+", messageId: "+messageId+
				", messageForumId: "+messageForumId);
		
		model.addAttribute("userDetailsId",userDetailsId);
		BigInteger currentMessageId=null;
		BigInteger forumId=null;
		Message message=null;
		// First time hitting the readMessage logic, look for an unread message
		if (messageForumId.isBlank()) {
			System.out.println("Looking for next unread message for userId:"+userDetailsId);
			BigInteger detailsId = new BigInteger(userDetailsId);
			BigInteger[] results = lrmService.getNextForumWithUnreadMessages(detailsId, BigInteger.ZERO);
			if (results != null && results.length==2 && !results[0].equals(BigInteger.ZERO)) {
				forumId=results[0];
				currentMessageId=results[1];
			}
		} else {
			currentMessageId=new BigInteger(messageId);
			forumId = new BigInteger(messageForumId);
		}
		System.out.println("Message ID: "+currentMessageId+", Message Forum ID: "+forumId);
		
		// Logic for going to prev/next forum
		if (action.equalsIgnoreCase("NextForum")) {
			forumId=new BigInteger(nextForumId);
			currentMessageId=new BigInteger(nextMessageId);
			action="next";
		} else if (action.equalsIgnoreCase("PrevForum") && !forumId.equals(BigInteger.ONE)) {
			// This will change to SQL to search backwards
			forumId=new BigInteger(prevForumId);
			Optional<Message> msg=mService.getLastMessageInMessageForum(forumId);
			if (msg.isPresent()) {
				// for previous logic, it looks for a number lower than currentMessageId.
				// So, to get last msg, we need last messageId + 1
				currentMessageId = msg.get().getId().add(BigInteger.ONE);
			}
			action="prev";
		}
		
		// Gets next or previous message 
		if (forumId != null) {
			message = getMesage(action, forumId, currentMessageId);
			if (message == null) {
				System.out.println("ERROR: Message is null!");
			} else {
				BigInteger detailsId = new BigInteger(userDetailsId);
				setNavigation(detailsId,message.getId(), message.getMessageForum().getId(), model);			
			}
			model.addAttribute("message",message);
		}

		model.addAttribute("menus",MenuUtilities.getMenus());
		return "messages/readMessage";
	}
	
	private void setNavigation(BigInteger detailsId, BigInteger currentMessageId, 
			BigInteger currentForumId, Model model) {
		
		Optional<Message> mOptional = mService.findPrevInMessageForum(currentMessageId, currentForumId);
		if (mOptional.isPresent()) {
			model.addAttribute("hasPrev", mOptional.get().getId());
		} else {
			if (currentForumId.compareTo(BigInteger.ONE)==1) {
				Optional<Message> mLast = null;
				BigInteger priorForumId = currentForumId;
				do {
					priorForumId = priorForumId.subtract(BigInteger.ONE);
					mLast = mService.getLastMessageInMessageForum(priorForumId);
				} while (mLast.isEmpty() && priorForumId.compareTo(BigInteger.ONE)==1);
				if (mLast.isPresent()) {
					model.addAttribute("prevMessageId",mLast.get().getId());
					model.addAttribute("prevForumId", priorForumId);
				}
			}
		}
		
		// Is there another message in the current forum?
		mOptional = mService.findNextInMessageForum(currentMessageId,currentForumId);
		if (mOptional.isPresent()) {
			model.addAttribute("hasNext", mOptional.get().getId());
		} else {
			// Is there a next forum with unread messages?
			BigInteger[] results = lrmService.getNextForumWithUnreadMessages(detailsId, currentForumId);
			if (results != null && results.length==2 && !results[0].equals(BigInteger.ZERO)) {
				BigInteger nextForumIdWithMsgs=results[0];
				BigInteger lastReadId=results[1];
				model.addAttribute("nextForumId", nextForumIdWithMsgs);
				model.addAttribute("nextMessageId", lastReadId);
			} else {
				System.out.println("No more messages");
			}
		}
	}
	
	private Message getMesage(String action, BigInteger forumId, BigInteger currentId) {
		System.out.println("getMessage: Action is ["+action+"]");
		Optional<Message> moptional=null;
		if (action.equalsIgnoreCase("next") || action.equals("")) {
			//System.out.println("getMessage: Getting next message from forum "+forumId+". Current message is "+currentId+".");
			moptional = mService.findNextInMessageForum(currentId,forumId);
		} else if (action.equalsIgnoreCase("prev")) {
			//System.out.println("getMessage: Getting prev message from forum "+forumId+". Current message is "+currentId+".");
			moptional = mService.findPrevInMessageForum(currentId, forumId);
		}
		Message message  = null;
		if (moptional.isPresent()) {
			message = moptional.get();
			//System.out.println("getMessage: Found a real message! id:"+message.getId());

		}
		return message;
	}	
}
