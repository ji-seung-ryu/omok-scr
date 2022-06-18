package com.mysite.chatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("/")
	public String redirectLogin() {
		return "redirect:/login";
	}
	
	@GetMapping("/home/userList")
	public String getUserList() {
		return "userList";
	}
	
	@GetMapping("/home/omok")
	public String getOmok() {
		return "omok";
	}
	
	
}
