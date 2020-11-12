package library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import library.model.Author;
import library.repository.AuthorRepository;
import library.service.AuthorService;

@Service
public class JpaAuthorService implements AuthorService{
	
	@Autowired
	private AuthorRepository AuthorRepository;

	@Override
	public Author getOne(Long id) {
		return AuthorRepository.getOne(id);
	}

	@Override
	public List<Author> findAll() {
		return AuthorRepository.findAll();
	}
	
	@Override
	public Page<Author> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return AuthorRepository.findAll(pageable);
	}

	@Override
	public Author save(Author Author) {
		return AuthorRepository.save(Author);
	}


	@Override
	public Author delete(Long id) {
		Author Author = AuthorRepository.getOne(id);
		if(Author != null) {
			AuthorRepository.delete(Author);
		}
		return Author;
	}

	@Override
	public Page<Author> search(String name, String country, int pageNum) {
		if(name != null) {
			name = '%' + name + '%';
		}
		if(country != null) {
			country = '%' + country + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 5);
		return AuthorRepository.search(name, country, pageable);
	}

	@Override
	public Author findByName(String name) {
		return AuthorRepository.findByName(name);
	}

}
