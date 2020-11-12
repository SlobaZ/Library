package library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import library.model.Author;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
	
	Author findByName(String name);

	@Query("SELECT a FROM Author a WHERE "
			+ "(:name IS NULL or a.name like :name ) AND "
			+ "(:country IS NULL OR a.country like :country) "
			)
	Page<Author> search(
			@Param("name") String name, 
			@Param("country") String country, 
			Pageable pageRequest);
	
}
