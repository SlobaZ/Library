package library.service;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import library.model.Book;

public interface BookService { 
	
	Book getOne(Long id);
	List<Book> findAll();
	Page<Book> findAll(int pageNum);
	Book save(Book Book);
	Book delete(Long id);
	
	List<Book> findByUserId(Long userId);
	
	Page<Book> search(
			@Param("userId") Long userId, 
			@Param("authorId") Long authorId, 
			@Param("name") String name, 
			@Param("numberOfPages") Integer numberOfPages,
			 int pageNum);

	Book alreadyExists(
			@Param("name") String name, 
			@Param("authorId") Long authorId
			);
	
	Book rent(Long id , int numberofpieces);

}
