function switchPage(dir) {
	
	console.log(dir);

	var currentPage = new URLSearchParams(window.location.search).get('page');

	if (typeof currentPage !== "number") {
		//currentPage = 0;
	}

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
		}else{
			currentUrl.searchParams.set('page', +currentPage + 1);
		}

	}

	window.location.href = currentUrl.href;
}