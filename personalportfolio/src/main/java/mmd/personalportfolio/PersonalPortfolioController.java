package mmd.personalportfolio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonalPortfolioController {
	/*
		@RequestMapping(value = "/css/{name}")
		public String getCSS(@PathVariable String name) {
			return "css/" + name + ".css";
		}
	*/
	
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
