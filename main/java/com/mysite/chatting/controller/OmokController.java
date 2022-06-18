package com.mysite.chatting.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.chatting.model.OmokRoom;
import com.mysite.chatting.service.OmokService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/omok")
@Controller
@RequiredArgsConstructor
public class OmokController {
	private final OmokService omokService;

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
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "omok";
    }
    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public OmokRoom roomInfo(@PathVariable String roomId) {
        return omokService.findById(roomId);
    }
}
