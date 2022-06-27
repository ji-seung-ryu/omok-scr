package com.mysite.chatting.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.mysite.chatting.model.OmokRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OmokService {
	private Map<String, OmokRoom> omokRooms;

	@PostConstruct
	private void init() {
		omokRooms = new LinkedHashMap<>();
	}

	public List<OmokRoom> findAllRoom(){
		List<OmokRoom> result = new ArrayList<>(omokRooms.values());
		Collections.reverse(result);

		return result;
	}
	
	public OmokRoom findById(String roomId) {
		return omokRooms.get(roomId);
	}
	
	public OmokRoom createRoom(String name) {
		OmokRoom omokRoom = OmokRoom.create(name);
		omokRooms.put(omokRoom.getRoomId(), omokRoom);
		return omokRoom;
	}
	public List<String> roomInfo (String roomId){
		return omokRooms.get(roomId).getMemberList();
	}
	
	public void enterRoomById (String roomId, String username){
		OmokRoom omokRoom = omokRooms.get(roomId);
		omokRoom.enter(username);	
	}
	
	public List<String> getUserListById (String roomId){
		OmokRoom omokRoom  = omokRooms.get(roomId);
		return omokRoom.getMemberList(); 
	}
	
	public String getTurn (String roomId) {
		OmokRoom omokRoom = omokRooms.get(roomId);
		return omokRoom.getTurn(); 
	}
	
	public int[][] getBoard (String roomId){
		OmokRoom omokRoom = omokRooms.get(roomId);
		return omokRoom.getBoard();
	}
	
	public void setBoard (int y, int x, int turn, String roomId) {
		OmokRoom omokRoom = omokRooms.get(roomId);
		omokRoom.putStone(y,x,turn);
		
	}
	
}
