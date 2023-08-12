package mmd.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import mmd.models.Article;
import mmd.repositories.ArticleRepository;

@Service
public class ArticleService {
	
	ArticleRepository ar;

	public ArticleService(ArticleRepository ar) {
		super();
		this.ar = ar;
	}
	
	public boolean viewArticle(Model model, int page) {
		Pageable pageable = PageRequest.of(page, 10);

		Iterable<Article> articleList = ar.findAll(pageable);

		model.addAttribute("articleList", articleList);
		
		return true;
	}
	
	public boolean editArticle(Model model, String id) {
		Article article = ar.getReferenceById(id);

		model.addAttribute("article", article);

		// User calls editArticle endpoint, so we set isEdit to true for the frontend
		// render
		model.addAttribute("isEdit", true);
		
		return true;
	}
	
	public boolean createArticle(Model model) {
		
		model.addAttribute("IsEdit", false);
		
		return true;
		
	}
	
	// TODO: handle IndexOutOfBounds exception
	public boolean readArticle(Model model, String id) {
		model.addAttribute("article", ar.getReferenceById(id));
		return true;
	}

}
