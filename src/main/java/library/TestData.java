package library;

import java.sql.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library.model.Author;
import library.model.Book;
import library.model.Role;
import library.model.User;
import library.security.UserService;
import library.service.AuthorService;
import library.service.BookService;
import library.utils.AdditionalClass;
import library.repository.RoleRepository;





@Component
public class TestData {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthorService authorService;
		
	@Autowired
	private BookService bookService;
	
	@PostConstruct
	public void init() {
		
		Role role1 = new Role();
		role1.setName("ROLE_ADMIN");
		role1 = roleRepository.save(role1);
		
		Role role2 = new Role();
		role2.setName("ROLE_USER");
		role2 = roleRepository.save(role2);
		
		User user1 = new User();
		user1.setUsername("Admin");
		user1.setFirstname("Administrator");
		user1.setLastname("Administrator");
		user1.setEmail("admin@gmail.com");
		user1.setPassword("$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS");
		user1.setCity("AdminMesto");
		user1.setAddress("AdminAdresa");
		user1.setPhone("064112233");
		user1 = userService.save(user1);

		User user2 = new User();
		user2.setUsername("VasaVasic");
		user2.setFirstname("Vasa");
		user2.setLastname("Vasic");
		user2.setEmail("vasa@gmail.com");
		user2.setPassword("$2a$10$bwQVsArIQJtmkPckmfRZGOEMAGBXcHaziXIEgstc9ePsPG6sYEFK.");
		user2.setCity("Beograd");
		user2.setAddress("Vojvode Milenka 30");
		user2.setPhone("065112233");
		user2 = userService.save(user2);
		
		User user3 = new User();
		user3.setUsername("PeraPeric");
		user3.setFirstname("Pera");
		user3.setLastname("Peric");
		user3.setEmail("pera@gmail.com");
		user3.setPassword("$2a$10$Locf9fRBO84ejEc/bQFEROChVsd2ixjv4M2kYX6KSLp74iacK.N3W");
		user3.setCity("Nis");
		user3.setAddress("Cara Dusana 45");
		user3.setPhone("063112233");
		user3 = userService.save(user3);
		
	
		user1.addRole(role1);
		user1.addRole(role2);
		user2.addRole(role2);
		user3.addRole(role2);
		userService.save(user1);
		userService.save(user2);
		userService.save(user3);

		
		Author author1 = new Author();
		author1.setName("Ivo Andric");
		author1.setCountry("Bosna");
		author1 = authorService.save(author1);
		
		Author author2 = new Author();
		author2.setName("Milos Crnjanski");
		author2.setCountry("Srbija");
		author2 = authorService.save(author2);
		
		Author author3 = new Author();
		author3.setName("Dobrica Cosic");
		author3.setCountry("Srbija");
		author3 = authorService.save(author3);
		
		Author author4 = new Author();
		author4.setName("Branislav Nusic");
		author4.setCountry("Srbija");
		author4 = authorService.save(author4);
		



		Book book1 = new Book();
		book1.setCode(AdditionalClass.assignAcode());
		book1.setName("Na Drini cuprija");
		book1.setNumberOfPages(318);
		book1.setDateOfPublication(Date.valueOf("1945-03-25"));
		book1.setQuantity(5);
		book1.setUser(user2);
		book1.setAuthor(author1);
		bookService.save(book1);
		
		Book book2 = new Book();
		book2.setCode(AdditionalClass.assignAcode());
		book2.setName("Seobe");
		book2.setNumberOfPages(786);
		book2.setDateOfPublication(Date.valueOf("1929-01-25"));
		book2.setQuantity(5);
		//book2.setUser(user2);
		book2.setAuthor(author2);
		bookService.save(book2);
		
		Book book3 = new Book();
		book3.setCode(AdditionalClass.assignAcode());
		book3.setName("Koreni");
		book3.setNumberOfPages(376);
		book3.setDateOfPublication(Date.valueOf("1954-08-25"));
		book3.setQuantity(5);
		//book3.setUser(user3);
		book3.setAuthor(author3);
		bookService.save(book3);
		
		Book book4 = new Book();
		book4.setCode(AdditionalClass.assignAcode());
		book4.setName("Gospodja ministarka");
		book4.setNumberOfPages(150);
		book4.setDateOfPublication(Date.valueOf("1929-05-25"));
		book4.setQuantity(5);
		book4.setUser(user3);
		book4.setAuthor(author4);
		bookService.save(book4);
		

	}

}
