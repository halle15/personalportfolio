package mmd.filters;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MessageInterceptor implements HandlerInterceptor {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        
        System.out.println(clientIp);
        
        if (!clientIp.equals("192.168.1.1")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return true;
    }
	
}
