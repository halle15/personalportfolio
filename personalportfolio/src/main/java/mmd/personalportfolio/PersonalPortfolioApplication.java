package mmd.personalportfolio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mmd.models.Message;
import mmd.repositories.MessageRepository;


@SpringBootApplication
public class PersonalPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalPortfolioApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(MessageRepository repo) {
		return (args) -> {
			for(int i = 0; i < 100; i++) {
			repo.save(new Message("Michael", "mball@balls.com", "Hello!"));
			}
			System.out.println("REPO: " + repo.findAll());
			
		};
		
	}
	
}

 