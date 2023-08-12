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
import mmd.services.MailNotificationService;
import mmd.services.MessageService;
import mmd.services.MiscService;

@Controller
public class PersonalPortfolioController {

	Map<String, CrudRepository<?, ?>> repositoryMap = new HashMap<>();

	IPRateLimiter limiter;

	private MailNotificationService notificationService;
	private MiscService miscService;
	private MessageService messageService;

	PersonalPortfolioController(MessageRepository messageRepo, ArticleRepository articleRepo, IPRateLimiter limiter,
			MailNotificationService notificationService, MiscService miscService, MessageService messageService) {

		repositoryMap.put("messageRepo", messageRepo);
		repositoryMap.put("articleRepo", articleRepo);

		this.messageService = messageService;
		this.notificationService = notificationService;
		this.miscService = miscService;

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

		// notificationService.sendSimpleMessage("New message from " + inMsg.getName(),
		// "Contact Information: " + inMsg.getContactInfo() + "\n\nMESSAGE: " +
		// inMsg.getMessage());

		return "redirect:/contact";
	}

	// ADMIN SECTION

	@GetMapping(value = "/admin")
	public String admin(Model model) {
		return "admin";
	}

	@GetMapping(value = "/admin/messages")
	public String adminMessageViewer(Model model, @RequestParam(defaultValue = "0") int page) {
		MessageRepository mr = (MessageRepository) repositoryMap.get("messageRepo");

		System.out.println(mr.countByIsReadFalse());

		Pageable pageable = PageRequest.of(page, 10);

		Iterable<Message> messageList = mr.findAll(pageable);

		model.addAttribute("messageList", messageList);
		model.addAttribute("newMessageCount", mr.countByIsReadFalse());

		/*
		 * for(Message message : messageList) { message.setRead(true); }
		 */

		mr.saveAll(messageList);

		return "messageviewer";
	}

	@PostMapping(value = "/admin/messages/read")
	public ResponseEntity<Void> markMessageAsRead(@RequestParam long id) {
		MessageRepository mr = (MessageRepository) repositoryMap.get("messageRepo");
		Message message = mr.findById(id);
		if (message != null) {
			message.setRead(true);
			mr.save(message);
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
		MessageRepository mr = (MessageRepository) repositoryMap.get("messageRepo");

		List<Long> idsInRange = LongStream.rangeClosed(lowId, highId).boxed().collect(Collectors.toList());

		List<Message> messageList = (List<Message>) mr.findAllById(idsInRange);

		System.out.println(messageList.toString());

		if (!messageList.isEmpty()) {
			for (Message m : messageList) {
				m.setRead(true);
			}
			mr.saveAll(messageList);
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
		MessageRepository mr = (MessageRepository) repositoryMap.get("messageRepo");

		List<Long> idsInRange = LongStream.rangeClosed(lowId, highId).boxed().collect(Collectors.toList());

		List<Message> messageList = (List<Message>) mr.findAllById(idsInRange);

		System.out.println(messageList.toString());

		if (!messageList.isEmpty()) {
			mr.deleteAll(messageList);
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

	@GetMapping(value = "/admin/articles")
	public String adminArticleViewer(Model model, @RequestParam(defaultValue = "0") int page) {
		ArticleRepository ar = (ArticleRepository) repositoryMap.get("articleRepo");

		Pageable pageable = PageRequest.of(page, 10);

		Iterable<Article> articleList = ar.findAll(pageable);

		model.addAttribute("articleList", articleList);

		return "adminarticleviewer";
	}

	@GetMapping(value = "/admin/editArticle")
	public String adminArticleEditor(Model model, @RequestParam(defaultValue = "0") String id) {
		ArticleRepository articleRepository = (ArticleRepository) repositoryMap.get("articleRepo");

		Article ar = articleRepository.getReferenceById(id);

		model.addAttribute("article", ar);

		// User calls editArticle endpoint, so we set isEdit to true for the frontend
		// render
		model.addAttribute("isEdit", true);

		return "article";
	}

	@GetMapping(value = "/admin/newArticle")
	public String adminArticleCreator(Model model) {
		model.addAttribute("isEdit", false);

		return "article";
	}

	@GetMapping(value = "/admin/readArticle")
	public String adminArticleReader(Model model, @RequestParam(defaultValue = "0") String id) {
		ArticleRepository ar = (ArticleRepository) repositoryMap.get("articleRepo");
		model.addAttribute("article", ar.getReferenceById(id));

		System.out.println(ar.getReferenceById(id).getText());
		// TODO: We need to find a way to handle the IndexOutOfBoundsException
		return "adminreadarticle";
	}

	// /admin/articles

	// /admin/editArticle

	// /admin/addArticle

	// /admin/readArticle for like testing and stuff?

	// maybe a specific message section?

}
