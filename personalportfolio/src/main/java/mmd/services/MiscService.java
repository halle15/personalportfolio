package mmd.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MiscService {
	
    public boolean ipCheck(HttpServletRequest request, Authentication auth) {
        
        if (auth != null && auth.isAuthenticated()) {
            return true;
        }

        String ipAddress = request.getHeader("X-Real-IP");
        if ("192.168.1.1".equals(ipAddress) || "47.224.71.176".equals(ipAddress)) {
            return true;
        }

        return false;
    }
	
}
