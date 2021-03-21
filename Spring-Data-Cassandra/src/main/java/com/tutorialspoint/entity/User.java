package com.tutorialspoint.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user")
public class User {
	@PrimaryKey
	private UserKey key;
	@Column("first_name")
	private String firstName;
	private String email;

	public User() {

	}

	public User(UserKey key, String firstName, String email) {
		this.key = key;
		this.firstName = firstName;
		this.email = email;
	}

	public UserKey getKey() {
		return key;
	}

	public void setKey(UserKey key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [key=" + key + ", firstName=" + firstName + ", email=" + email + "]";
	}

}
