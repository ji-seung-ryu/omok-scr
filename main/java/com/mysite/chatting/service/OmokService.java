package com.mysite.chatting.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.mysite.chatting.model.OmokRoom;

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
}