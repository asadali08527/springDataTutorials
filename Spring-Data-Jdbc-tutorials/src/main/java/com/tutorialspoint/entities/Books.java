package com.tutorialspoint.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class Books {

	@Id
	private Long id;
	private String isbn;
	private String title;
	private Set<AuthorsReference> authors = new HashSet<AuthorsReference>();

	public void addAuthor(Authors authors) {
		this.authors.add(new AuthorsReference(authors.getId()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<AuthorsReference> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<AuthorsReference> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return "Books [id=" + id + ", isbn=" + isbn + ", title=" + title + ", authors=" + authors + "]";
	}

}
