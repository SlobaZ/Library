package library.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import library.model.Author;
import library.repository.AuthorRepository;
import library.service.AuthorService;


@Controller
public class AuthorsController {

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private AuthorRepository authorRepository;


	@GetMapping("/authorsAll")
	public String getAll(Model model) {
		model.addAttribute("authors", authorService.findAll());
		return "authors";
	}

		
	@RequestMapping(value = "/authors", method = RequestMethod.GET)
	public String searchAuthors(@RequestParam (required = false) String name,
										@RequestParam (required = false) String country,
										HttpServletRequest request, Model model) {
	    
		int page = 0;
		int size = 5;
	            
	    Page<Author> authorPage = null;
	
			if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
				page = Integer.parseInt(request.getParameter("page")) - 1;
			}

			if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
				size = Integer.parseInt(request.getParameter("size"));
			}
		    if (name != null || country != null ) {
		    	authorPage = authorService.search(name, country, page);
		    }
		    else {
		    	authorPage = authorRepository.findAll(PageRequest.of(page, size));
		    }
		    
	    model.addAttribute("authors", authorPage);
	    return "authors";
	}
	



		
	
	
	
	
	

}
