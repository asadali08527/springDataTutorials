package com.tutorialspoint.entities;

import org.springframework.data.annotation.Id;

public class Address {
	@Id
	private Long id;

	private String addressLine;

	public Address(String addressLine) {
		this.addressLine = addressLine;
	}

	@Override
	public String toString() {
		return "Address [address_line=" + addressLine + "]";
	}

}
