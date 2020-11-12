package library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{ 

	List<Book> findByUserId(Long userId); 

	@Query("SELECT b FROM Book b WHERE "
			+ "(:userId IS NULL or b.user.id = :userId ) AND "
			+ "(:authorId IS NULL or b.author.id = :authorId ) AND "
			+ "(:name IS NULL OR b.name like :name) AND "
			+ "(:numberOfPages IS NULL or b.numberOfPages <= :numberOfPages ) "
			)
	Page<Book> search(
			@Param("userId") Long userId, 
			@Param("authorId") Long authorId, 
			@Param("name") String name, 
			@Param("numberOfPages") Integer numberOfPages,
			Pageable pageRequest);
	
	
	@Query("SELECT b FROM Book b WHERE "
			+ " b.name = :name  AND "
			+ " b.author.id = :authorId "
			)
	Book alreadyExists(
			@Param("name") String name, 
			@Param("authorId") Long authorId
			);
	
}
