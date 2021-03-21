package com.tutorialspoint.es.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "tutorials_point")
public class Customer {
	@Id
	private Long id;
	private String name;
	private String email;
	private Double salary;
	@Field(type = FieldType.Nested, includeInParent = true)
	private List<Address> addresses;

	public Customer() {
	}

	public Customer(Long id, String name, String email, Double salary, List<Address> addresses) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.salary = salary;
		this.addresses = addresses;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", salary=" + salary + ", addresses="
				+ addresses + "]";
	}

}
