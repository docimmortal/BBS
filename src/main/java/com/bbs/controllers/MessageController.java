package com.bbs.controllers;

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

import com.bbs.entites.Details;
import com.bbs.entites.Message;
import com.bbs.services.DetailsService;
import com.bbs.services.MessageService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MessageController {

	@Autowired
	private MessageService mService;
	
	@Autowired
	private DetailsService dService;
	
	@PostMapping("/readMessage")
	public String readMessage(@RequestParam(required = false, defaultValue="") String id, Model model, HttpServletRequest request) {
		
		if (id.isEmpty()) { // we are coming from "/navigate"s
			Object obj = request.getAttribute("id");
			id = obj.toString();
		}
		Optional<Details> optional = dService.findOptionalByUsername("bob");
		Details d = optional.get();
		Optional<Message> moptional = mService.findById(Integer.parseInt(id));
		Message message  = null;
		if (moptional.isPresent()) {
			System.out.println("Found a real message!");
			message = moptional.get();
		} else {
			message = new Message(); // temp for now.
		}
		model.addAttribute("message",message);
		return "messages/readMessage";
	}
	
	@PostMapping("/navigate")
	public ModelAndView navigate(@RequestParam String action, @RequestParam String messageId,
			@RequestParam String messageForumId, HttpServletRequest request) {
		System.out.println("Read next message...");
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		ModelAndView mav = new ModelAndView("forward:/readMessage");
		Integer iid = Integer.parseInt(messageId);
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
