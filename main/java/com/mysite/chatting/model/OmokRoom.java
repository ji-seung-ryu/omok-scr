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
	
	private String whoTurn;
	private int isRunning, isBlack;

	
	
	
	public static OmokRoom create(String name) {
		OmokRoom room = new OmokRoom();
		room.roomId = UUID.randomUUID().toString();
		room.roomName = name;
		return room;
	}
	
	public void enter(String username){		
		if (!memberList.contains(username)) memberList.add(username);		
	}
	
	public void putStone (int y, int x, int turn) {
		if (isBlack ==1 ) turn =1;
		else turn = 2;
		
		board[y][x] = turn;
		System.out.println("y: "+y+ "x: "+x +"turn: " + turn);
		if (isBlack == 1) isBlack = 0;
		else isBlack = 1;
		
		for (String member : memberList) {
			if (member != whoTurn) {
				whoTurn = member;
				break;
			}
		}
	}
	
	public void gameStart () {
		isRunning = 1;
		isBlack = 1;
		whoTurn = memberList.get(0);
		
	}
	
}
