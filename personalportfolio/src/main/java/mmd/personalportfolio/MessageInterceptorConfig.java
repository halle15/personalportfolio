package mmd.personalportfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import mmd.filters.IPRateLimiter;


@Configuration
public class MessageInterceptorConfig implements WebMvcConfigurer{
	
	@Bean
	public IPRateLimiter ipRateLimiter() {
		return new IPRateLimiter();
	}
}

