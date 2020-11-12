package library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import library.model.Book;
import library.repository.BookRepository;
import library.service.BookService;

@Service
public class JpaBookService implements BookService{ 
	 
	@Autowired
	private BookRepository bookRepository;
	
	
	
	@Override
	public Book getOne(Long id) {
		return bookRepository.getOne(id);
	}
	
	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}


	@Override
	public Page<Book> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return bookRepository.findAll(pageable); 
	}

	@Override
	public Book save(Book Book) {
		return bookRepository.save(Book);
	}

	@Override
	public Book delete(Long id) {
		Book Book = bookRepository.getOne(id);
		if(Book != null) {
			bookRepository.delete(Book);
		}
		return Book;
	}
	

	@Override
	public List<Book> findByUserId(Long userId) {
		return bookRepository.findByUserId(userId);
	}

		

	@Override
	public Page<Book> search(Long userId, Long authorId, String name, Integer numberOfPages, int pageNum) {
		if(name != null) {
			name = '%' + name + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return bookRepository.search(userId, authorId, name, numberOfPages, pageable);
	}



	@Override
	public Book rent(Long id , int numberofpieces) { 
		
		Book book = bookRepository.getOne(id);
		
		if ( book.getQuantity() >= numberofpieces && book.getQuantity() > 0 ) {

			book.setQuantity(book.getQuantity() - numberofpieces);
		} 
		else {
			return null;
		}

		bookRepository.save(book);
		
		return book;

	}

	@Override
	public Book alreadyExists(String name, Long authorId) {
		return bookRepository.alreadyExists(name, authorId);
	}
 





	
}
