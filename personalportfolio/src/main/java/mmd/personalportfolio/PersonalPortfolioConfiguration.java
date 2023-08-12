package mmd.personalportfolio;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EntityScan(basePackages = "mmd.models")
@ComponentScan(basePackages = { "mmd.filters", "mmd.services" })
@EnableJpaRepositories(basePackages = { "mmd.repositories" })
@EnableAsync
public class PersonalPortfolioConfiguration implements WebMvcConfigurer {

}
