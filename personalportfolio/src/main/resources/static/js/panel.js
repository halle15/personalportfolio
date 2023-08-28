document.addEventListener('DOMContentLoaded', function() {

	// Form Formatting

	// check for edit status from server
	let isEdit = false;
	isEdit = document.getElementById("article-box").getAttribute("isEdit");


	function getRemovedBrackets(input) {
		if (input.startsWith('[') && input.endsWith(']')) {
			return input.slice(1, -1);
		}
		return input;
	}

	if (isEdit) {
		let tagsInput = document.getElementById("tags");
		let imgsInput = document.getElementById("images");

		// Get the new values from the input fields
		let newTags = tagsInput.value;
		let newImgs = imgsInput.value;

		// Modify the values as needed
		newTags = getRemovedBrackets(newTags);
		newImgs = getRemovedBrackets(newImgs);

		// Update the input fields with the new values
		tagsInput.value = newTags;
		imgsInput.value = newImgs;
	}

	// Form Submission
	if (!isEdit) {
		$('form').submit(function(event) {
			event.preventDefault();

			var formData = {
				title: $('#title').val(),
				author: $('#author').val(),
				desc: $('#desc').val(),
				text: $('#text').val(),
				mainImg: $('#mainImg').val(),
				images: $('#images').val().split(','),
				mainTag: $('#mainTag').val(),
				tags: $('#tags').val().split(','),
				pubDate: $('#pubDate').val()
			};

			$.ajax({
				type: 'POST',
				url: '/articles',
				data: JSON.stringify(formData),
				contentType: 'application/json',
				success: function(response) {
					alert('Article edited successfully!');
					window.location.href = "/admin/articles"
				},
				error: function(error) {
					alert('An error occurred while adding the article.\n LOG: ' + error);
					console.log(error);
				}
			});
		});
	}
	else {
		
		let id = new URLSearchParams(window.location.search).get('id');
		let targetUrl = "/articles/" + id;
		
		$('form').submit(function(event) {
			event.preventDefault();

			var formData = {
				title: $('#title').val(),
				author: $('#author').val(),
				desc: $('#desc').val(),
				text: $('#text').val(),
				mainImg: $('#mainImg').val(),
				images: $('#images').val().split(','),
				mainTag: $('#mainTag').val(),
				tags: $('#tags').val().split(','),
				pubDate: $('#pubDate').val()
			};

			$.ajax({
				type: 'PUT',
				url: targetUrl,
				data: JSON.stringify(formData),
				contentType: 'application/json',
				success: function(response) {
					alert('Article added successfully!');
					window.location.href = "/admin/articles"
					// Optionally redirect to another page or perform any other action
				},
				error: function(error) {
					alert('An error occurred while editing the article.\n LOG: ' + error);
					console.log(error);
				}
			});
		});
	}
});
