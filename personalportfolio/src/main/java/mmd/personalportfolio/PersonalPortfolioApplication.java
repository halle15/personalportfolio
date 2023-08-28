package mmd.personalportfolio;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mmd.models.Article;
import mmd.models.Message;
import mmd.repositories.ArticleRepository;
import mmd.repositories.MessageRepository;


@SpringBootApplication
public class PersonalPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalPortfolioApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(MessageRepository repo, ArticleRepository ar) {
		return (args) -> {
			for(int i = 0; i < 50; i++) {
			repo.save(new Message("Michael the " + i + "th", "mball@balls.com", "Hello!"));
			}
			System.out.println("REPO: " + repo.findAll());
		};
		
	}
	
}

 