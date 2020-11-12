package library.model;

import java.sql.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String code;
	@Column
	private String name;
	@Column
	private Integer numberOfPages;
	@Column
	private Date dateOfPublication;
	@Column
	private Integer quantity;


	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	private Author author;
	
		
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public Date getDateOfPublication() {
		return dateOfPublication;
	}

	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
		/*
		if (!author.getBooks().contains(this)) {
			author.getBooks().add(this);
		}
		*/
	}

	

}
