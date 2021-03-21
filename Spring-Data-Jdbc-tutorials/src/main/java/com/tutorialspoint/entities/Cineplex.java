package com.tutorialspoint.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cineplex")
public class Cineplex implements Serializable {
	@Id
	private Long id;
	@Column("movie_title")
	private String movieTitle;
	@Column("lead_actor")
	private String leadActor;
	@Column("movie_duration")
	private Integer movieDuration;
	@Column("ticket_price")
	private Double ticketPrice;

	public Cineplex() {
	}

	public Cineplex(Long id, String movieTitle, String leadActor, Integer movieDuration, Double ticketPrice) {
		super();
		this.id = id;
		this.movieTitle = movieTitle;
		this.leadActor = leadActor;
		this.movieDuration = movieDuration;
		this.ticketPrice = ticketPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getLeadActor() {
		return leadActor;
	}

	public void setLeadActor(String leadActor) {
		this.leadActor = leadActor;
	}

	public Integer getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(Integer movieDuration) {
		this.movieDuration = movieDuration;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	@Override
	public String toString() {
		return "Cineplex [id=" + id + ", movieTitle=" + movieTitle + ", leadActor=" + leadActor + ", movieDuration="
				+ movieDuration + ", ticketPrice=" + ticketPrice + "]";
	}

}
