package mmd.personalportfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import mmd.filters.IPRateLimiter;
import mmd.filters.MessageFilter;


@Configuration
public class MessageInterceptorConfig implements WebMvcConfigurer{
	
	@Bean
    public FilterRegistrationBean<MessageFilter> filterRegistrationBean() {
        FilterRegistrationBean<MessageFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MessageFilter());
        registrationBean.addUrlPatterns("/messages");
        return registrationBean;
    }
	
	@Bean
	public IPRateLimiter ipRateLimiter() {
		return new IPRateLimiter();
	}
}

