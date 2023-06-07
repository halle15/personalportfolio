document.addEventListener('DOMContentLoaded', function() {

	/*function rs() {  deprecated, issues with navbar
		const navbar = document.querySelector('.navbar');
		const form = document.querySelector('.all');
		form.style.marginTop = navbar.clientHeight + 'px';
	}
	
	rs();
	
	window.addEventListener('resize', function() {
		rs();
	});
	*/

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
				alert('Article added successfully!');
				// Optionally redirect to another page or perform any other action
			},
			error: function(error) {
				alert('An error occurred while adding the article.\n LOG: ' + error);
				console.log(error);
			}
		});
	});
});
