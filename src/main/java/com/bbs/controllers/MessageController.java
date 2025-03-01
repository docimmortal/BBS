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
			@RequestParam(required = false, defaultValue="") String prevForumId,
			Model model, HttpServletRequest request) {
		
		BigInteger currentMessageId=null;
		BigInteger forumId=null;
		// First time hitting the readMessage logic
		if (messageForumId.isBlank()) {
			System.out.println("Looking for next unread message for userId:"+userDetailsId);
			BigInteger detailsId = new BigInteger(userDetailsId);
			BigInteger[] results = lrmService.getNextForumWithUnreadMessages(detailsId, BigInteger.ZERO);
			forumId=results[0];
			currentMessageId=results[1];
		} else {
			currentMessageId=new BigInteger(messageId);
			forumId = new BigInteger(messageForumId);
		}
		System.out.println("Message ID: "+currentMessageId+", Message Forum ID: "+forumId);
		
		// Logic for going to prev/next forum
		if (action.equalsIgnoreCase("NextForum")) {
			forumId=new BigInteger(nextForumId);
			// for next logic, it looks for a number greater than currentMsgId
			currentMessageId=new BigInteger("0");
			action="next";
			System.out.println("Went to [NextForum] to ["+action+"].");
		} else if (action.equalsIgnoreCase("PrevForum") && !forumId.equals(BigInteger.ONE)) {
			forumId=new BigInteger(prevForumId);
			Optional<Message> msg=mService.getLastMessageInMessageForum(forumId);
			if (msg.isPresent()) {
				// for previous logic, it looks for a number lower than currentMessageId.
				// So, to get last msg, we need last messageId + 1
				currentMessageId = msg.get().getId().add(BigInteger.ONE);
			}
			action="prev";
			System.out.println("Went to [PrevForum] to ["+action+"].");
		}
		
		// Gets next or previous message
		Message message = getMesage(action, forumId, currentMessageId);
		if (message == null) {
			System.out.println("ERROR: Message is null!");
		} else {
			setNavigation(message.getId(), message.getMessageForum().getId(), model);
		}

		model.addAttribute("message",message);
		model.addAttribute("menus",MenuUtilities.getMenus());
		return "messages/readMessage";
	}
	
	private void setNavigation(BigInteger currentMessageId, BigInteger currentForumId, Model model) {
		
		Optional<Message> mOptional = mService.findPrevInMessageForum(currentMessageId, currentForumId);
		if (mOptional.isPresent()) {
			model.addAttribute("hasPrev", mOptional.get().getId());
		} else {
			if (!currentForumId.equals(BigInteger.ONE)) {
				// is there a previous forum with messages
			}
		}
		
		mOptional = mService.findNextInMessageForum(currentMessageId,currentForumId);
		if (mOptional.isPresent()) {
			model.addAttribute("hasNext", mOptional.get().getId());
		} else {
			// is there a next forum with messages
			BigInteger nextForumIdWithMsgs = findNextForum(currentForumId, model);
			if (!nextForumIdWithMsgs.equals(BigInteger.ZERO)) {
				model.addAttribute("nextForumId", nextForumIdWithMsgs);
			}
		}
	}
	
	private Message getMesage(String action, BigInteger forumId, BigInteger currentId) {
		System.out.println("getMessage: Action is ["+action+"]");
		Optional<Message> moptional=null;
		if (action.equalsIgnoreCase("next") || action.equals("")) {
			System.out.println("getMessage: Getting next message from forum "+forumId+". Current message is "+currentId+".");
			moptional = mService.findNextInMessageForum(currentId,forumId);
		} else if (action.equalsIgnoreCase("prev")) {
			System.out.println("getMessage: Getting prev message from forum "+forumId+". Current message is "+currentId+".");
			moptional = mService.findPrevInMessageForum(currentId, forumId);
		}
		Message message  = null;
		if (moptional.isPresent()) {
			message = moptional.get();
			System.out.println("getMessage: Found a real message! id:"+message.getId());

		}
		return message;
	}
	
	private BigInteger findNextForum(BigInteger currentForumId, Model model) {
		System.out.println("Previous forum: "+currentForumId);
		// check to see if a next forum exist
		boolean nextForumExists = mfService.existsNextMessageForum(currentForumId);
		boolean nextForumHasMessage = false;
		BigInteger nextForumId = new BigInteger(currentForumId.toString());
		while (nextForumExists && !nextForumHasMessage) {
			
			nextForumId=nextForumId.add(BigInteger.ONE); // get next forum number
			// hard code message of next forum to 0 to find next message in that forum
			nextForumHasMessage = mService.existsNextInMessageForum(BigInteger.ZERO, nextForumId);
			if (nextForumHasMessage) {
				System.out.println("Forum "+nextForumId+" has messages!");
			} else {
				System.out.println("Forum "+nextForumId+" has no new messages!");
			}
			if (!nextForumHasMessage) {
				// That forum does not have next messages. Try next one, if one exists.
				nextForumExists = mfService.existsNextMessageForum(nextForumId);
			}
			System.out.println("Current forum is now: "+currentForumId+" ("+nextForumHasMessage+")");
		}
		if (nextForumHasMessage) {
			model.addAttribute("nextForumId",nextForumId);
		}
		if (!nextForumExists) {
			nextForumId = BigInteger.ZERO;
		}
		return nextForumId;
	}
	
	@PostMapping("/navigate")
	public ModelAndView navigate(@RequestParam String userDetailsId,
			@RequestParam String action, @RequestParam String messageId,
			@RequestParam String messageForumId, HttpServletRequest request) {
		System.out.println("Read next message...");
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		ModelAndView mav = new ModelAndView("forward:/readMessage");
		Integer iid = Integer.parseInt(messageId);
		//Integer fid = Integer.parseInt(messageForumId);
		// pretend to find next iid.
		if (action.equalsIgnoreCase("Next")) {
			iid++;
		} else if (action.equalsIgnoreCase("Previous") && iid>1) {
			iid--;
		}
		request.setAttribute("id", iid);
		request.setAttribute("userDetailsId", userDetailsId);
		return mav;
	}
		
}
