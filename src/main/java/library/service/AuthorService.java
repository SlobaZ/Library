package library.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import library.model.Author;

public interface AuthorService {

	Author getOne(Long id);
	List<Author> findAll();
	Page<Author> findAll(int pageNum);
	Author save(Author Author);
	Author delete(Long id);
	
	Page<Author> search(
			@Param("name") String name, 
			@Param("country") String country, 
			 int pageNum);
	
	Author findByName(String name);
	
}
