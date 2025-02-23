package com.bbs.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bbs.entites.UserDetails;
import com.bbs.entites.Message;
import com.bbs.entites.MessageForum;
import com.bbs.entites.Reaction;
import com.bbs.entites.Reply;
import com.bbs.enums.ReactionType;
import com.bbs.services.DetailsService;
import com.bbs.services.MessageForumService;
import com.bbs.services.MessageService;
import com.bbs.services.ReplyService;
import com.bbs.utilities.MenuUtilities;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MessageController {

	@Autowired
	private MessageService mService;
	
	@Autowired
	private MessageForumService mfService;
	
	@PostMapping("/readMessage")
	public String readMessage(@RequestParam(required = false, defaultValue="0") String messageId, 
			@RequestParam(required = false, defaultValue="1") String messageForumId,
			Model model, HttpServletRequest request) {
		
		System.out.println("Message ID: "+messageId+", Message Forum ID: "+messageForumId);
		if (messageId.isEmpty()) { // we are coming from "/navigate"s
			Object obj = request.getAttribute("messageId");
			messageId = obj.toString();
		}
		
		Optional<Message> moptional = mService.findNextInMessageForum(new BigInteger(messageId),new BigInteger(messageForumId));
		Message message  = null;
		if (moptional.isPresent()) {
			System.out.println("Found a real message!");
			message = moptional.get();
		} else {
			message = new Message(); // temp for now.
		}
		boolean hasNext = mService.existsNextInMessageForum(message.getId(), message.getMessageForum().getId());
		if (!hasNext) {
			BigInteger currentForum=message.getMessageForum().getId();
			System.out.println("Previous forum: "+currentForum);
			// check to see if a next forum exist
			boolean nextForum = mfService.existsNextMessageForum(currentForum);
			boolean nextForumHasMessage = false;
			while (nextForum && !nextForumHasMessage) {
				currentForum=currentForum.add(BigInteger.ONE); // get next forum number
				// hard code message of next forum to 0 to find next message in that forum
				nextForumHasMessage = mService.existsNextInMessageForum(BigInteger.ZERO, currentForum);
				if (nextForumHasMessage) {
					System.out.println(" and it has messages!");
				} else {
					System.out.println(" but it has no new messages!");
				}
				if (!nextForumHasMessage) {
					// That forum does not have next messages. Try next one, if one exists.
					nextForum = mfService.existsNextMessageForum(currentForum);
				}
			}
			if (nextForumHasMessage) {
				model.addAttribute("nextForumHasMessage", nextForumHasMessage);
				model.addAttribute("nextForumId",currentForum);
			}
		}
		
		model.addAttribute("message",message);
		model.addAttribute("hasNext", hasNext);
		model.addAttribute("menus",MenuUtilities.getMenus());
		return "messages/readMessage";
	}
	
	@PostMapping("/navigate")
	public ModelAndView navigate(@RequestParam String action, @RequestParam String messageId,
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
		return mav;
	}
		
}
