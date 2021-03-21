package com.tutorialspoint.document;

public class Review {

	private String customerName;
	private Integer rating;
	private boolean approved;

	public Review() {
	}

	public Review(String customerName, Integer rating, boolean approved) {
		this.customerName = customerName;
		this.rating = rating;
		this.approved = approved;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "Review [ customerName=" + customerName + ", rating=" + rating + ", approved=" + approved + "]";
	}

}
