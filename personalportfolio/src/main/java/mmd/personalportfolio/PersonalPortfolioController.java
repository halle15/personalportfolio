package mmd.personalportfolio;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mmd.filters.IPRateLimiter;
import mmd.models.Article;
import mmd.models.Message;
import mmd.repositories.ArticleRepository;
import mmd.repositories.MessageRepository;
import mmd.services.ArticleService;
import mmd.services.MailNotificationService;
import mmd.services.MessageService;
import mmd.services.MiscService;

@Controller
public class PersonalPortfolioController {


	IPRateLimiter limiter;

	private MailNotificationService notificationService;
	private MiscService miscService;
	private MessageService messageService;
	private ArticleService articleService;

	public PersonalPortfolioController(IPRateLimiter limiter, MailNotificationService notificationService, MiscService miscService, MessageService messageService,
			ArticleService articleService) {
		super();
		this.limiter = limiter;
		this.notificationService = notificationService;
		this.miscService = miscService;
		this.messageService = messageService;
		this.articleService = articleService;
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

	@GetMapping(value = { "/", "/home", "/index" })
	public String index(HttpServletRequest request, Model model, Authentication auth) {

		if (miscService.ipCheck(request, auth)) {
			model.addAttribute("isAdminIP", true);
		}
		return "index";

	}

	@GetMapping(value = "/login")
	public String login(Model model) {
		return "login";
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
			@RequestParam String contactInfo, @RequestParam String message) {

		Message inMsg = new Message(name, contactInfo, message);

		if (!messageService.saveMessage(inMsg, request)) {
			return "redirect:/error";
		}

		return "redirect:/contact";
	}

	/*
	 * Admin Section
	 * 
	 * 
	 */

	@GetMapping(value = "/admin")
	public String admin(Model model) {
		return "admin";
	}

	@GetMapping(value = "/admin/messages")
	public String adminMessageViewer(Model model, @RequestParam(defaultValue = "0") int page) {

		messageService.readMessages(model, page);

		return "messageviewer";
	}

	@PostMapping(value = "/admin/messages/read")
	public ResponseEntity<Void> markMessageAsRead(@RequestParam long id) {
		if (messageService.markMessageAsRead(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * test curl request curl -X POST -H
	 * "Content-Type: application/x-www-form-urlencoded" -d "lowId=1&highId=10"
	 * http://dev.evannhall.dev/admin/messages/readMany
	 */
	@PostMapping(value = "/admin/messages/readMany")
	public ResponseEntity<Void> markMultipleMessageAsRead(@RequestParam long lowId, long highId) {
		if (messageService.markMultipleMessageAsRead(lowId, highId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * test curl request curl -X POST -H
	 * "Content-Type: application/x-www-form-urlencoded" -d "lowId=1&highId=10"
	 * http://dev.evannhall.dev/admin/messages/deleteMany
	 */
	@PostMapping(value = "/admin/messages/deleteMany")
	public ResponseEntity<Void> deleteMultipleMessages(@RequestParam long lowId, long highId) {
		if (messageService.deleteMultipleMessages(lowId, highId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/admin/email")
	public String sendEmail() {
		notificationService.sendSimpleMessage("BUTTON HIT!", "AY YOU HIT DA BUTTON CUH!");
		return "redirect:/admin";
	}

	/*
	 * 	Article Section
	 * 
	 * 
	 */
		
	
	@GetMapping(value = "/admin/articles")
	public String adminArticleViewer(Model model, @RequestParam(defaultValue = "0") int page) {
		
		articleService.viewArticle(model, page);
		
		return "adminarticleviewer";
	}

	@GetMapping(value = "/admin/editArticle")
	public String adminArticleEditor(Model model, @RequestParam(defaultValue = "0") String id) {
		
		articleService.editArticle(model, id);

		return "article";
	}

	@GetMapping(value = "/admin/newArticle")
	public String adminArticleCreator(Model model) {
		
		articleService.createArticle(model);
		
		return "article";
	}

	@GetMapping(value = "/admin/readArticle")
	public String adminArticleReader(Model model, @RequestParam(defaultValue = "0") String id) {
		
		articleService.readArticle(model, id);

		return "adminreadarticle";
	}

	// /admin/articles

	// /admin/editArticle

	// /admin/addArticle

	// /admin/readArticle for like testing and stuff?

	// maybe a specific message section?

}
