package library.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import library.service.AuthorService;
import library.service.BookService;
import library.utils.AdditionalClass;
import library.model.Author;
import library.model.Book;
import library.model.User;
import library.repository.UserRepository;
import library.security.UserService;

@Controller
public class AdminController {  
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	

		
	
	

	@GetMapping(value="/admin/addauthor")
    public ModelAndView createNewAuthor(){
        ModelAndView modelAndView = new ModelAndView();
        Author author = new Author(); 
        modelAndView.addObject("author", author);
        modelAndView.setViewName("/admin/author-add");
        return modelAndView;
    }
	
    @PostMapping(value = "/admin/addauthor")
    public ModelAndView addNewAuthor(@Valid Author author, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Author authorExists = authorService.findByName(author.getName()); 
        if (authorExists != null) {
            bindingResult.rejectValue("name", "error.name",  "There is already a author added with the naziv provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/admin/author-add");
        } 
        else {
        	authorService.save(author);
            modelAndView.addObject("successMessage", "Author has been add successfully");
            modelAndView.addObject("author", new Author());
            modelAndView.setViewName("/admin/author-add");
        }
        return modelAndView;
    }

    
    @GetMapping("/admin/deleteauthor/{id}")
    private String deleteAuthor(@PathVariable("id") Long id){
    	Author  author = authorService.getOne(id);
    	if(author != null){
    		authorService.delete(id);
        	
        }else{
            System.err.println("not found");
        }
        return "redirect:/authors";
    }
    

    @GetMapping(path = {"/admin/editauthor/{id}"})
    private String addFormAuthor(@PathVariable("id") Long id, Model model){
        if(id != null){
            model.addAttribute("author", authorService.getOne(id) );
        }
        else{
        	return null;
        }
        return "/admin/author-edit";
    }
    
    
    @PostMapping("/admin/editAuthor")
    private String insertOrUpdateAuthor(@Valid Author author, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
        	return "/admin/author-edit";
        	} 
        else{
        	Author editAuthor = authorService.getOne(author.getId());
            if(editAuthor !=null){
            	editAuthor.setName(author.getName());
            	editAuthor.setCountry(author.getCountry());
            	authorService.save(editAuthor);
            }
        }
        return "redirect:/authors";

    }
    
    
    
    
    @GetMapping(value="/admin/addbook")
    public ModelAndView createNewBook(){
        ModelAndView modelAndView = new ModelAndView();
        Book book = new Book();
        book.setCode(AdditionalClass.assignAcode()); 
        modelAndView.addObject("book", book);  
        List<Author> authors = authorService.findAll();
        modelAndView.addObject("authors", authors);
        modelAndView.setViewName("admin/book-add");
        return modelAndView;
    }
	
    @PostMapping(value = "/admin/addbook")
    public ModelAndView addNewBook(@Valid Book book,  BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Book bookExists = bookService.alreadyExists(book.getName(),book.getAuthor().getId()); 
        if (bookExists != null) {
            bindingResult.rejectValue("name", "error.name",  "There is already a Book added");
        }
        if (bindingResult.hasErrors()) {
        	List<Author> authors = authorService.findAll();
            modelAndView.addObject("authors", authors);
            modelAndView.setViewName("admin/book-add");
        } 
        else {
        	bookService.save(book);
            modelAndView.addObject("successMessage", "Book has been add successfully");
            modelAndView.addObject("book", new Book());
            modelAndView.setViewName("admin/book-add");

        }
        return modelAndView;
    }

    
    @GetMapping("/admin/deletebook/{id}")
    private String deleteBook(@PathVariable("id") Long id){
    	Book  book = bookService.getOne(id);
        if(book != null){
        	bookService.delete(id);
        	
        }else{
            System.err.println("not found");
        }
        return "redirect:/books";
    }
    

    @GetMapping(path = {"/admin/editbook/{id}"})
    private String addFormBook(@PathVariable("id") Long id, Model model){
        if(id != null){
            model.addAttribute("book", bookService.getOne(id) );
            List<Author> authors = authorService.findAll();
            model.addAttribute("authors", authors);
        }
        else{
        	return null;
        }
        return "/admin/book-edit";
    }
    
    
    @PostMapping("/admin/editBook")
    private String insertOrUpdateBook(@Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
        	return "/admin/book-edit";
        	} 
        else{
        	Book editBook = bookService.getOne(book.getId());
            if(editBook !=null){
            	editBook.setCode(book.getCode());
            	editBook.setName(book.getName());
            	editBook.setNumberOfPages(book.getNumberOfPages());
            	editBook.setDateOfPublication(book.getDateOfPublication());
            	editBook.setQuantity(book.getQuantity());
            	editBook.setAuthor(book.getAuthor());
            	bookService.save(editBook);
            }
        }
        return "redirect:/books";
    }
    
    
      
    
        
	@GetMapping("/admin/usersAll")
	public String listaUsers(Model model) {
		model.addAttribute("users", userService.findAll());		
		return "/admin/user";
	}
	
	
	
	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String sviItrazeniUsers (@RequestParam (required = false) String username,
										@RequestParam (required = false) String city,
										HttpServletRequest request, Model model) {
	    
		int page = 0;
		int size = 5;
	    
	    Page<User> userPage = null;
	
			if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
				page = Integer.parseInt(request.getParameter("page")) - 1;
			}

			if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
				size = Integer.parseInt(request.getParameter("size"));
			}
		    if (username != null || city != null) {
		    	userPage = userService.search(username, city, page);
		    }
		    else {
		    	userPage = userRepository.findAll(PageRequest.of(page, size));
		    }
		    
	    model.addAttribute("users", userPage);
	    return "/admin/user";
	}
	

	
	 @GetMapping("/admin/deleteuser/{id}")
	    private String deleteUser(@PathVariable("id") Long id){
	    	User  user = userService.getOne(id);
	        if(user != null){
	        	userService.delete(id);
	        }else{
	            System.err.println("not found");
	        }
	        return "redirect:/admin/users";
	    }
	    

	    @GetMapping(path = {"/admin/edituser/{id}"})
	    private String addFormUser(@PathVariable("id") Long id, Model model){
	        if(id != null){
	            model.addAttribute("user", userService.getOne(id) );
	        }
	        else{
	        	return null;
	        }
	        return "/admin/user-edit";
	    }
	    
	    
	    @PostMapping("/admin/editUser")
	    private String insertOrUpdateUser(@Valid User user, BindingResult bindingResult){
	        if (bindingResult.hasErrors()) {
	        	return "/admin/user-edit";
	        	} 
	        else{
	        	User editUser = userService.getOne(user.getId());
	            if(editUser !=null){
	            	editUser.setUsername(user.getUsername());
	            	editUser.setFirstname(user.getFirstname());
	            	editUser.setLastname(user.getLastname());
	            	editUser.setEmail(user.getEmail());
	            	editUser.setPassword(user.getPassword());
	            	editUser.setCity(user.getCity());
	            	editUser.setAddress(user.getAddress());
	            	editUser.setPhone(user.getPhone());
	            	userService.saveUser(editUser);
	            }
	        }
	        return "redirect:/admin/users";
	    }
	
	

	    
	    
    
	  
	
    
}