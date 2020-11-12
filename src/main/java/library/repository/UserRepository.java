package library.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import library.model.Book;
import library.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	User findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	User checkByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE "
			+ "(:username IS NULL or u.username like :username ) AND "
			+ "(:city IS NULL OR u.city like :city) "
			)
	Page<User> search(
			@Param("username") String username, 
			@Param("city") String city,
			Pageable pageRequest);
	
	
	
	@Query("SELECT b FROM Book b WHERE b.user.id = :idU")
	Book userBook( @Param("idU") Long idU);
	
	

}
