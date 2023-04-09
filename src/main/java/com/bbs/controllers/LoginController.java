package com.bbs.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bbs.entites.Details;
import com.bbs.services.DetailsServiceImpl;

@Controller
public class LoginController {

	@Autowired
	private DetailsServiceImpl service;
	
	@GetMapping("/login")
	public String login() {
		//bob password is puser123
		//joe@king.com password is padm123
		//System.out.println(passwordEncoder.encode("padm123"));
		return "login";
	}
	
	@GetMapping("/hello")
	public String hello(Model model) throws IOException {
		String username = "Unknown";
		Details details = new Details();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    username = authentication.getName();
		    String role=authentication.getAuthorities().toString().substring(6);
		    System.out.println(role);
		    role=role.substring(0, role.length()-1);
		    System.out.println(role);
		    model.addAttribute("role",role);
		    Optional<Details> optional = service.findOptionalByUsername(username);
		    details = optional.get();
		}
		model.addAttribute("details", details);
		//BufferedImage bi = ImageUtilities.convertFromClob(details.getPhoto());
		//model.addAttribute("photo",bi);
		return "hello";
	}
}
