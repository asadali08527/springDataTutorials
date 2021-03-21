package com.tutorialspoint.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class Authors {

	@Id
	private Long id;
	private String name;
	private LocalDate dob;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Authors [id=" + id + ", name=" + name + ", dob=" + dob + "]";
	}

}
