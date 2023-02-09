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

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PersonalPortfolioController {

	MessageRepository repo;
	IPRateLimiter limiter;

	PersonalPortfolioController(MessageRepository repo, IPRateLimiter limiter) {
		this.repo = repo;
		this.limiter = limiter;
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

	@GetMapping(value = "/error")
	public String error() {
		return "error";
	}

	@PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public String putMessage(HttpServletRequest request, HttpServletResponse response, @RequestParam String name,
			@RequestParam String email, @RequestParam String message) {
		Message inMsg = new Message(name, email, message);
		
		if(!limiter.tryAcquire(request.getRemoteAddr())) {
			try {
				System.out.println("request error");
			response.sendError(429, "Too many requests!");
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return "error";
		}
		
		repo.save(inMsg);
		
		return "contact";

	}
}
