package mmd.services;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import mmd.filters.IPRateLimiter;
import mmd.models.Message;
import mmd.repositories.MessageRepository;

@Service
public class MessageService {
	
	MessageRepository mr;
	IPRateLimiter limiter;
	
	public MessageService(MessageRepository mr, IPRateLimiter limiter) {
		super();
		this.limiter = limiter;
		this.mr = mr;
	}

	public boolean saveMessage(Message message, HttpServletRequest request) {
		if(!limiter.tryAcquire(request.getRemoteAddr())) {
			return false;
		}
		
		mr.save(message);
		
		return true;
	}
}
