package library.web.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import library.model.Book;
import library.model.User;
import library.security.UserService;
import library.service.BookService;


@Controller
public class RentController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;

    
    
	@RequestMapping(value = "/user/rent/{id}", method = RequestMethod.GET)
	public String rent(@PathVariable("id") Long id,
						@RequestParam (required = false, defaultValue = "0") int numberofpieces,
						Model model, Principal principal) {
		User user =  userService.checkByEmail(principal.getName());
		Book book = bookService.getOne(id);
				
		if(book.getQuantity() >= numberofpieces && book.getQuantity() > 0 && numberofpieces!=0) {
			bookService.rent(id,numberofpieces);
			model.addAttribute("messageToTheUser",  user.getUsername()  + " / " + " " + user.getFirstname() + " " + user.getLastname()   );
			model.addAttribute("book", book);    
		return "/user/result";
		}
		else {
			return "redirect:/books";
		}
	}
    
    
    
}
