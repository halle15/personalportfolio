package mmd.personalportfolio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PersonalportfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalportfolioApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(MessageRepository repo) {
		return (args) -> {
			repo.save(new Message("Michael", "mball@balls.com", "Hello!"));
			System.out.println("REPO: " + repo.findAll());
			
		};
		
	}
	
}

 