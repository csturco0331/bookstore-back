package com.pluralsight.bookstore.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Book {

	@Id @GeneratedValue
	private Long id;
	
	@Column(length = 200)
	private String title;
	
	@Column(length = 1000)
	private String description;
	
	@Column(name = "unit_cost")
	private Float unitCost;

	private String isbn; 
	
	@Column(name = "publication_date")
	@Temporal(TemporalType.DATE)
	private Date publicationDate;
	
	@Column(name = "nb_of_pages")
	private Integer nbOfPages;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	private Language language;
	
}
