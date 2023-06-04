package mmd.personalportfolio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // TODO: find a way to remove this
			.authorizeHttpRequests((requests) -> requests
					
					.requestMatchers("/putmessage").permitAll()
		            .requestMatchers(HttpMethod.GET, "/message", "/messages", "/blog", "/admin").authenticated()
		            .anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin_user =
				User.withDefaultPasswordEncoder()
				.username("halle15")
				.password("javamichaelhall")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(admin_user);
	}
}
