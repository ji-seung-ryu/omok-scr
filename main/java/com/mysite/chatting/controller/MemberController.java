package com.mysite.chatting.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mysite.chatting.model.Member;
import com.mysite.chatting.model.Member.statusType;
import com.mysite.chatting.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes("username")
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/")
	public String redirectLogin() {
		return "redirect:/login";
	}
	
	@PostMapping("/login")
    @ResponseBody
    public Member createMember(@RequestParam String name) {
        return memberService.createMember(name);
    }

	@GetMapping("/home/userList")
	public String getUserList(Model model, @RequestParam String userId) {
		
		System.out.println(userId);
		System.out.println(memberService.findById(userId).getName());
		
		// 이 전에도 방문이 있었다면.. 세션으로 하는게 유리하겠구나 로그아웃까지 생각하면 
		
		if (memberService.findById(userId).getStatus() == statusType.ACTIVE) {
			model.addAttribute("already",true);
		}
		// 세션이 있다면 model.addAttribute로 체크 
		
		model.addAttribute("username", memberService.findById(userId).getName());
		model.addAttribute("userList", memberService.findAllMember());
		return "userList";
	}

	@PostMapping("/home/userList")
	public String changeStatus (Model model, @RequestParam String userId ) {
		memberService.changeStatusById(userId, statusType.INACTIVE);
		model.addAttribute("username", memberService.findById(userId).getName());
		model.addAttribute("userList", memberService.findAllMember());
		return "userList";
	}

}
