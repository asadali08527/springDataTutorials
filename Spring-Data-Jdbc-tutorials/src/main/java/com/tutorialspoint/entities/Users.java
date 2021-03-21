package com.tutorialspoint.entities;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Users {
	@Id
	private Long id;
	@Column("full_name")
	private String fullName;
	@Column("date_of_birth")
	private LocalDate dob;
	private Set<Address> address;

	/**
	 * 
	 * @param id
	 * @param fullName
	 * @param dob
	 * 
	 *                 It is recommended that entity has single all argument
	 *                 constructor for the framework itself. and other constructors
	 *                 are modeled a static factory method
	 */
	public Users(Long id, String fullName, LocalDate dob, Set<Address> address) {
		this.id = id;
		this.fullName = fullName;
		this.dob = dob;
		this.address = address;
	}

	public static Users create(String name, LocalDate dateOfBirth, Set<Address> address) {
		return new Users(null, name, dateOfBirth, address);
	}

	public void addAddress(Address address) {
		this.address.add(address);
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", fullName=" + fullName + ", dob=" + dob + ", address=" + address + "]";
	}

	public void setName(String name) {
		this.fullName = name;
	}

}
