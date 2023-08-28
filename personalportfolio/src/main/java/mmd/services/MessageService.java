package mmd.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import mmd.filters.IPRateLimiter;
import mmd.models.Message;
import mmd.repositories.MessageRepository;

@Service
public class MessageService {
	
	MessageRepository mr;
	IPRateLimiter limiter;
	
	MailNotificationService notificationService;
	SettingsService settingsService;
	
	public MessageService(MessageRepository mr, IPRateLimiter limiter, MailNotificationService notificationService,
			SettingsService settingsService) {
		super();
		this.mr = mr;
		this.limiter = limiter;
		this.notificationService = notificationService;
		this.settingsService = settingsService;
	}

	public boolean saveMessage(Message message, HttpServletRequest request) {
		if(!limiter.tryAcquire(request.getRemoteAddr())) {
			return false;
		}
		
		mr.save(message);
		
		if(settingsService.getEmailStatus()) {
			notificationService.sendSimpleMessage("New message from " + message.getName(), "Contact Information: " + message.getContactInfo() + "\n\nMESSAGE: " + message.getMessage());
		}
		
		return true;
	}
	
	public void readMessages(Model model, int page) {
		Pageable pageable = PageRequest.of(page, 10);

		Iterable<Message> messageList = mr.findAll(pageable);

		model.addAttribute("messageList", messageList);
		model.addAttribute("newMessageCount", mr.countByIsReadFalse());
	}
	
	public boolean markMessageAsRead(long id) {
		Message message = mr.findById(id);
		if (message != null) {
			message.setRead(true);
			mr.save(message);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean markMultipleMessageAsRead(long lowId, long highId) {
		List<Long> idsInRange = LongStream.rangeClosed(lowId, highId).boxed().collect(Collectors.toList());

		List<Message> messageList = (List<Message>) mr.findAllById(idsInRange);

		System.out.println(messageList.toString());

		if (!messageList.isEmpty()) {
			for (Message m : messageList) {
				m.setRead(true);
			}
			mr.saveAll(messageList);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteMultipleMessages(long lowId, long highId) {
		List<Long> idsInRange = LongStream.rangeClosed(lowId, highId).boxed().collect(Collectors.toList());

		List<Message> messageList = (List<Message>) mr.findAllById(idsInRange);

		System.out.println(messageList.toString());

		if (!messageList.isEmpty()) {
			mr.deleteAll(messageList);
			return true;
		}
		
		return false;
	}
	
}
