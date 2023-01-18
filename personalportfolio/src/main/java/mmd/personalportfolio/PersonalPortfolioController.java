package mmd.personalportfolio;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(value = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String name) throws IOException {
        var imgFile = new ClassPathResource("images/" + name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }
	
	  @GetMapping(value = "/")
		public String index() {
			return "index";
		}
	  
	  @GetMapping(value = "/home")
		public String index1() {
			return "index";
		}
}
