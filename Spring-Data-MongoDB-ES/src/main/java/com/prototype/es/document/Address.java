package com.prototype.es.document;

public class Address {

	private String city;
	private String country;
	private Integer zipCode;

	public Address() {
	}

	public Address(String city, String country, Integer zipCode) {
		this.city = city;
		this.country = country;
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Address [city=" + city + ", country=" + country + ", zipCode=" + zipCode + "]";
	}

}
