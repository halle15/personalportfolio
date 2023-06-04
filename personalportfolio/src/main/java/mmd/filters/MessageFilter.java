package mmd.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class MessageFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String endpoint = httpRequest.getRequestURI();

		String inboundIP = request.getRemoteAddr();
		
		System.out.println(inboundIP);

		if (inboundIP.equals("192.168.1.1") || inboundIP.equals("0:0:0:0:0:0:0:1")) {

			// allow access to all other endpoints
			System.out.println("passed");
			chain.doFilter(request, response);
		} 
		else {
			throw new ServletException("Access to this endpoint is restricted");
		}
	}
}
