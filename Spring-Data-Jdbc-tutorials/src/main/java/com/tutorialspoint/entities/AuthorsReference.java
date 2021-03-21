package com.tutorialspoint.entities;

import org.springframework.data.relational.core.mapping.Table;

@Table("book_author")
public class AuthorsReference {

	private Long author;

	public AuthorsReference(Long author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "AuthorsReference [author=" + author + "]";
	}

}
