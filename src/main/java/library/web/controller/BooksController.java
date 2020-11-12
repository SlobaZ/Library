package library.web.controller;

import java.util.List;

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
import library.model.Book;
import library.model.User;
import library.repository.BookRepository;
import library.security.UserService;
import library.service.AuthorService;
import library.service.BookService;

@Controller
public class BooksController {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorService authorService;

	@GetMapping("/booksAll")
	public String getAll(Model model) {
		model.addAttribute("books", bookService.findAll());
		return "books";
	}

		
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String searchBooks(@RequestParam (required = false) Long userId,
										@RequestParam (required = false) Long authorId,
										@RequestParam ( required = false) String name,
										@RequestParam ( required = false) Integer numberOfPages,
										HttpServletRequest request, Model model) {
	    
		int page = 0;
		int size = 5;
	            
	    Page<Book> bookPage = null;
	    List<User> users = userService.findAll();
        model.addAttribute("users", users);
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
	
			if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
				page = Integer.parseInt(request.getParameter("page")) - 1;
			}

			if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
				size = Integer.parseInt(request.getParameter("size"));
			}
		    if (userId != null || authorId != null || name != null || numberOfPages != null) {
		    	bookPage = bookService.search(userId, authorId, name, numberOfPages, page);
		    }
		    else {
		    	bookPage = bookRepository.findAll(PageRequest.of(page, size));
		    }
		    
	    model.addAttribute("books", bookPage);
	    return "books";
	}
	


	
	
	
	

}
