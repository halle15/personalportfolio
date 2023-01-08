package mmd.personalportfolio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalPortfolioController {
	  @GetMapping(value = "/")
		public String index() {
		  System.out.println("FUCK");
			return "index";
		}
	  
	  @GetMapping(value = "/home")
		public String index1() {
			return "index";
		}
}
