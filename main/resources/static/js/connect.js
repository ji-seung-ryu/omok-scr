'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = localStorage.getItem('username');

var colors = [
	'#2196F3', '#32c787', '#00BCD4', '#ff5652',
	'#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect() {
	if (username) {
		var socket = new SockJS('/javatechie');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, onConnected, onError);
	}

}


function onConnected() {
	stompClient.subscribe('/topic/public', onMessageReceived);
	stompClient.subscribe('/topic/' + username, onMessageReceived);

	// Tell your username to the server

	stompClient.send("/app/chat.register",
		{},
		JSON.stringify({ sender: username, receiver: username, type: 'JOIN' })
	)

}


function onError(error) {
	connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
	connectingElement.style.color = 'red';
}

function sendRequest(receiver) {

	var o = { 'title': 'request' };

	if (stompClient) {
		var chatMessage = {
			sender: username,
			content: JSON.stringify(o),
			type: 'CHAT',
			receiver: receiver
		}

		stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
	}
}
function sendRespond(receiver, ok) {
	var o = { 'title': 'respond', 'ok': ok };
	if (stompClient) {
		var chatMessage = {
			sender: username,
			content: JSON.stringify(o),
			type: 'CHAT',
			receiver: receiver
		}

		stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
	}
}

function send(event) {
	//  var messageContent = messageInput.value.trim();
	console.log(event);
	console.log(typeof (event));
	var e = new Object({ "offsetX": event.offsetX, "offsetY": event.offsetY });

	console.log(e);

	if (stompClient) {
		var chatMessage = {
			sender: username,
			content: JSON.stringify(e),
			type: 'CHAT'
		};

		stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
		// messageInput.value = '';
	}
	event.preventDefault();
}


function onMessageReceived(payload) {
	var message = JSON.parse(payload.body);


	if (message.type === 'JOIN') {
		message.content = message.sender + ' joined!';
		if (message.sender === username) setMember(message.members);
		else addMember(message.sender);
	} else if (message.type === 'LEAVE') {
		deleteMember(message.sender);




	} else {
		var content = JSON.parse(message.content);
		var ok = 0;
		if (content.title === 'request') {
			if (ok = confirm(message.sender + "와의 대국 하시겠습니까?")) {
				sendRespond(message.sender, ok);
				location.href = "/home/omok"

			}
			else sendRespond(message.sender, ok);
		} else if (content.title === 'respond') {
			if (content.ok) {
				location.href = "/home/omok"
					
			} else {
				console.log('싱대가 거절');
			}

		} else {
			console.log('not request, respond');
		}

		

	}


}


function disConnect() {
	if (stompClient) {
		var leaveMessage = {
			sender: username,
			content: null,
			type: 'LEAVE'
		};

		stompClient.send("/app/chat.leave", {}, JSON.stringify(leaveMessage));
		stompClient.unsubscribe();
	}

}

connect();
window.addEventListener('beforeunload', disConnect, true);