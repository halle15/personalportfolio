function switchPage(dir) {


	var currentPage = new URLSearchParams(window.location.search).get('page');

	if (dir !== 0 && dir !== 1) {
		console.error("Direction not registered!");
	}

	var currentUrl = new URL(window.location.href);
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
	xhr.send();}