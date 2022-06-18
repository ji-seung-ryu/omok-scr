package com.mysite.chatting.model;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OmokRoom {

	private String roomId;
	private String roomName;

	public static OmokRoom create(String name) {
		OmokRoom room = new OmokRoom();
		room.roomId = UUID.randomUUID().toString();
		room.roomName = name;
		return room;
	}
}
