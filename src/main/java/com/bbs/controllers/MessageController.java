package com.bbs.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bbs.entites.Details;
import com.bbs.entites.Message;
import com.bbs.entites.MessageForum;
import com.bbs.services.DetailsService;
import com.bbs.services.MessageForumService;

@Controller
public class MessageController {

	@Autowired
	private MessageForumService mfService;
	
	@Autowired
	private DetailsService dService;
	
	@GetMapping("/readMessage")
	public String readMessage(@RequestParam String id, Model model) {
		
		Optional<Details> optional = dService.findOptionalByUsername("bob");
		Details d = optional.get();
		Optional<MessageForum> optional1 = mfService.findById(1);
		MessageForum mf=optional1.get();
		Message message = new Message("Hello","This is a test.", d, mf);
		message.setId(Integer.parseInt(id));
		System.out.println(message);
		System.out.println(message.getForum());
		model.addAttribute("message",message);
		return "messages/readMessage";
	}
	
	@GetMapping("/navigate")
	public ModelAndView navigate(@RequestParam String action, @RequestParam String messageId,
			@RequestParam String mesageForumId) {
		ModelAndView mav = new ModelAndView("/readMessage");
		
		// pretend to navigate and check next messageId for that same forum
		Integer iid = Integer.parseInt(messageId);
		iid++;
		mav.addObject("id", messageId);
		
		return mav;
	}
		
}
