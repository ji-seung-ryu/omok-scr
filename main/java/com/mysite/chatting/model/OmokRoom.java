package com.mysite.chatting.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OmokRoom {

	private String roomId;
	private String roomName;
	private List <String> memberList = new ArrayList<String>();
	private final int BOARD_SIZE = 20; 	
	private final int STONE_BLACK = 1; 
	private final int STONE_WHITE = 2;
	
	private int [][] board = new int[BOARD_SIZE][BOARD_SIZE];
	
	private String turn;

	
	
	
	public static OmokRoom create(String name) {
		OmokRoom room = new OmokRoom();
		room.roomId = UUID.randomUUID().toString();
		room.roomName = name;
		return room;
	}
	
	public void enter(String username){
		if (memberList.isEmpty()) turn = username;
		
		if (!memberList.contains(username)) memberList.add(username);		
	}
	
	public void putStone (int y, int x, int turn) {
		board[y][x] = turn;
		System.out.println("y: "+y+ "x: "+x +"turn: " + turn);
		
	}
	
	
}
