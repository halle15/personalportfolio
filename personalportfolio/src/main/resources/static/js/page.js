function switchPage(dir) {

	let currentPage = new URLSearchParams(window.location.search).get('page');

	if (dir !== 0 && dir !== 1) {
		console.error("Direction not registered!");
	}

	let currentUrl = new URL(window.location.href);
	if (dir == 0) {
		if (currentPage > 0) {
			currentUrl.searchParams.set('page', +currentPage - 1);
		} else {
			currentUrl.searchParams.set('page', 0);
		}
	} else {
		if (currentPage < 0) {
			currentUrl.searchParams.set('page', 0);
		} else {
			currentUrl.searchParams.set('page', +currentPage + 1);
		}
	}

	window.location.href = currentUrl.href;
}
function deleteMessage(id) {
	let confirmed = confirm("Are you sure you want to delete this?");
	
	if(!confirmed){
		return;
	}
	
	const xhr = new XMLHttpRequest();
	xhr.open('DELETE', '/messages/' + id, true);
	xhr.setRequestHeader('Content-type', 'application/json');
	xhr.onload = function() {
		if (xhr.status === 204) {
			// Successful deletion
			alert('Message deleted successfully.');
			// Optionally, you can remove the deleted message from the DOM
			const messageBox = document.querySelector('[data-message-id="' + id + '"]').closest('.message-box');
			messageBox.remove();
		} else {
			// Error handling
			alert('Error deleting the message.');
		}
	};
	xhr.send();
}

function readMessage(id) {
	console.log(id);
  const xhr = new XMLHttpRequest();
  xhr.open('POST', '/admin/messages/read', true);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.setRequestHeader('Message-ID', id); // Set the ID as a request header
  xhr.onload = function() {
	const readStatus = document.querySelector('[data-message-id="' + id + '"]').parentNode.parentNode.querySelector('.read-indicator');
	if (readStatus.innerText === "Read") {
		alert("Error: Message already read!");
	}
    else if (xhr.status !== 200) {
      // Error handling
      alert('Error marking the message as read.');
    }
    else{
	readStatus.innerText = "Read";
	
	const messageCount = document.querySelector('.message-count');
	messageCount.innerText = messageCount.innerText - 1
}	
  };
  xhr.send('id=' + encodeURIComponent(id));
}

function readAll(){
	const messageIds = Array.from(document.querySelectorAll('.message-box .delete-button')).map(button => parseInt(button.getAttribute('data-message-id')));
	const lowId = Math.min(...messageIds);
	const highId = Math.max(...messageIds);
	
	if(!isFinite(lowId) || !isFinite(highId)){
		navigateToPreviousPage();
		return;
	}
	
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/messages/readMany', true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (xhr.status !== 200) {
            // Error handling
            alert('Error marking the messages as read.');
        } else {
            // Update UI as needed
            const messages = document.querySelectorAll('.message-box');
            messages.forEach(message => {
                const messageId = parseInt(message.querySelector('.read-button').getAttribute('data-message-id'), 10);
                if (messageId >= lowId && messageId <= highId) {
                    const readStatus = message.querySelector('.read-indicator');
                    if (readStatus.innerText !== "Read") {
                        readStatus.innerText = "Read";
                        const messageCount = document.querySelector('.message-count');
                        messageCount.innerText = parseInt(messageCount.innerText, 10) - 1;
                    }
                }
            });
        }
    };

    xhr.send(`lowId=${encodeURIComponent(lowId)}&highId=${encodeURIComponent(highId)}`);
}

function navigateToPreviousPage() {
    const currentUrl = new URL(window.location.href);
    let currentPage = parseInt(currentUrl.searchParams.get("page") || "0", 10); // Default to 0 if page param is missing

    // Decrement the page number and ensure it doesn't fall below 0
    currentPage = Math.max(0, currentPage - 1);

    // Update the page parameter in the URL
    currentUrl.searchParams.set("page", currentPage);

    // Navigate to the updated URL
    window.location.href = currentUrl.toString();
}

function deleteAll(){
	const messageIds = Array.from(document.querySelectorAll('.message-box .delete-button')).map(button => parseInt(button.getAttribute('data-message-id')));
	const lowId = Math.min(...messageIds);
	const highId = Math.max(...messageIds);
	
	if(!isFinite(lowId) || !isFinite(highId)){
		navigateToPreviousPage();
		return;
	}
	
	let confirmed = confirm("Are you sure you want to delete IDs " + lowId + " to " + highId);
	
	if(!confirmed){
		return;
	}
	
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/messages/deleteMany', true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (xhr.status !== 200) {
            // Error handling
            alert('Error deleting the messages');
        } else {        
            navigateToPreviousPage();
        }
    };

    xhr.send(`lowId=${encodeURIComponent(lowId)}&highId=${encodeURIComponent(highId)}`);
}


function deleteArticle(id) {
	
	let confirmed = confirm("Are you sure you want to delete this?");
	
	if(!confirmed){
		return;
	}
	
	
	const xhr = new XMLHttpRequest();
	xhr.open('DELETE', '/articles/' + id, true);
	xhr.setRequestHeader('Content-type', 'application/json');
	xhr.onload = function() {
		if (xhr.status === 204) {
			// Successful deletion
			alert('Message deleted successfully.');
			// Optionally, you can remove the deleted message from the DOM
			const messageBox = document.querySelector('[data-message-id="' + id + '"]').closest('.message-box');
			messageBox.remove();
		} else {
			// Error handling
			alert('Error deleting the message.');
		}
	};
	xhr.send();
}

function editArticle(id){
	let pageUrl = '/admin/editArticle?id=' + id;
	
	window.location.href = pageUrl;
}

function readArticle(id){
	let pageUrl = '/admin/readArticle?id=' + id;
	
	window.location.href = pageUrl;
}