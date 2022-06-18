function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
		localStorage.setItem('username', username);
		location.href = "/home/userList";
    }
    event.preventDefault();
}

var usernameForm = document.querySelector('#usernameForm');
usernameForm.addEventListener('submit', connect, true);
