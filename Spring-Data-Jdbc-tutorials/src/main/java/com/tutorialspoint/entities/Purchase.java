package com.tutorialspoint.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("purchase")
public class Purchase {
	@Id
	private Long id;
	private Integer quantity;
	@Column("show_time")
	private String showTime;
	@Column("price")
	private Double price;

	public Purchase() {
	}

	public Purchase(Long id, Integer quantity, String showTime, Double price) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.showTime = showTime;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", quantity=" + quantity + ", showTime=" + showTime + ", price=" + price + "]";
	}

}
