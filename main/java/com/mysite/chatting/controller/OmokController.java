package com.mysite.chatting.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.chatting.model.Member;
import com.mysite.chatting.model.OmokMessage;
import com.mysite.chatting.model.OmokRoom;
import com.mysite.chatting.service.OmokService;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException; // 예외처리
import org.json.simple.parser.JSONParser;
import lombok.RequiredArgsConstructor;

@RequestMapping("/omok")
@Controller
@RequiredArgsConstructor
public class OmokController {
	private final OmokService omokService;
	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/omok.register")
	@SendTo("/topic/omok")
	public void register(@Payload OmokMessage omokMessage, SimpMessageHeaderAccessor headerAccessor) {

		headerAccessor.getSessionAttributes().put("username", omokMessage.getSender());
		omokMessage.setMembers(omokService.roomInfo(omokMessage.getRoomId()));
		simpMessagingTemplate.convertAndSend("/topic/omok/" + omokMessage.getRoomId(), omokMessage);

		// members.add(chatMessage.getSender());
		// chatMessage.setMembers(members);

	}

	@MessageMapping("/omok.put")
	@SendTo("topic/omok")
	public void sendMessage(@Payload OmokMessage omokMessage) {
		omokMessage.setMembers(omokService.roomInfo(omokMessage.getRoomId()));
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject parsedJson = (JSONObject) parser.parse(omokMessage.getContent());
			
			System.out.println(parsedJson);
			int y = Integer.parseInt(String.valueOf(parsedJson.get("Y")));
			int x = Integer.parseInt(String.valueOf(parsedJson.get("X")));
			int turn = Integer.parseInt(String.valueOf(parsedJson.get("Turn")));
			
			
			omokService.putStone(y,x,turn,omokMessage.getRoomId());
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// int(parsedJson.get("turn")), omokMessage.getRoomId());

		simpMessagingTemplate.convertAndSend("/topic/omok/" + omokMessage.getRoomId(), omokMessage);
		// return chatMessage;
	}

	// 모든 채팅방 목록 반환
	@GetMapping("/rooms")
	@ResponseBody
	public List<OmokRoom> room() {
		return omokService.findAllRoom();
	}

	// 채팅방 생성
	@PostMapping("/create")
	@ResponseBody
	public OmokRoom createRoom(@RequestParam String name) {
		return omokService.createRoom(name);
	}

	// 채팅방 입장 화면
	@GetMapping("/room/enter/{roomId}")
	public String roomDetail(Model model, @PathVariable String roomId, @RequestParam String username) {
		omokService.enterRoomById(roomId, username);
		List <String> memberList = omokService.getUserListById(roomId);
		if (omokService.getIsRunning(roomId) == 1) {
			model.addAttribute("board",omokService.getBoard(roomId));
			model.addAttribute("username", username);
			model.addAttribute("whoTurn", omokService.getWhoTurn(roomId));
			model.addAttribute("isBlack", omokService.getIsBlack(roomId));
			model.addAttribute("memberList", memberList);
			model.addAttribute("omokRoomId", roomId);
			model.addAttribute("isRunning", 1);
			
			
			return "omok";
		} else if (memberList.size() == 2) {
			omokService.gameStart(roomId);
		//	omokService.setIsRunning(roomId, 1);
			model.addAttribute("board",omokService.getBoard(roomId));
			model.addAttribute("username", username);
			model.addAttribute("whoTurn", omokService.getWhoTurn(roomId));
			model.addAttribute("isBlack", omokService.getIsBlack(roomId));
			model.addAttribute("memberList", memberList);
			model.addAttribute("omokRoomId", roomId);
			model.addAttribute("isRunning", 1);

			
			return "omok";
			
		} else if (memberList.size() ==1 ){
			model.addAttribute("isRunning", 0);

			return "omok";

		} else {
			// userid 받아야 함. 나중에.. 
			return "/login";
		}
		
		//model.addAttribute("turn", turn);
		
	}

	// 특정 채팅방 조회
	@GetMapping("/room/{roomId}")
	@ResponseBody
	public OmokRoom roomInfo(@PathVariable String roomId) {
		return omokService.findById(roomId);
	}
}
