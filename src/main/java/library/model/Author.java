package library.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="author")
public class Author {
	
	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String name;
	@Column
	private String country;
	
	
	@OneToMany(mappedBy="author", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	List<Book> books = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books; 
	}
	
	public void addBook(Book book) {
		if(book.getAuthor() != this) {
			book.setAuthor(this);
		}
		books.add(book);
	}
	
	

}
