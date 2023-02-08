package mmd.personalportfolio;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonalPortfolioController {

	MessageRepository repo;

	PersonalPortfolioController(MessageRepository repo) {
		this.repo = repo;
	}
	/*
	 * @RequestMapping(value = "/css/{name}") public String getCSS(@PathVariable
	 * String name) { return "css/" + name + ".css"; }
	 */

	@RequestMapping(value = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> getImage(@PathVariable String name) throws IOException {
		var imgFile = new ClassPathResource("images/" + name);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
				.body(new InputStreamResource(imgFile.getInputStream()));
	}

	@GetMapping(value = "/")
	public String index() {
		return "index";
	}

	@GetMapping(value = "/home")
	public String home() {
		return "index";
	}

	@GetMapping(value = "/contact")
		public String contact() {
			return "contact";
		}

	@PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
		public String putMessage(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
		  	Message inMsg = new Message(name, email, message);
		
			repo.save(inMsg);
		    
			return "contact";
		}
}



