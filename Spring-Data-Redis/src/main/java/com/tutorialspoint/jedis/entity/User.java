package com.tutorialspoint.jedis.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("user")
public class User {
	public enum Gender {
		MALE, FEMALE
	}

	@Id
	private Long id;
	private String name;
	@Indexed
	private Gender gender;
	private int marks;

	public User(String name, Gender gender, int marks) {
		this.name = name;
		this.gender = gender;
		this.marks = marks;
	}

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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

}
