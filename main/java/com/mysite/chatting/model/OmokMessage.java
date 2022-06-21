package com.mysite.chatting.model;

import java.util.Vector;

public class OmokMessage {
	private String content;
	private String sender;
	private String receiver;
	private String roomId; 
	private MessageType type;
	private Vector<String> members = new Vector<String>();  

	public enum MessageType {
		PUT, JOIN, LEAVE;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public String getRoomId() {
		return roomId;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId; 
	}
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Vector<String> getMembers(){
		return members;
	}
	
	public void setMembers(Vector<String> members) {
		this.members = members;
	}
}
