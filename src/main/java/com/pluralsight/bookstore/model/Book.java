package com.pluralsight.bookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "Book resource respresentation")
public class Book {

	@Id @GeneratedValue
	@ApiModelProperty("Identifier")
	private Long id;
	
	@Column(length = 200)
	@NotNull
	@Size(min=1, max=200)
	@ApiModelProperty("Title of the book")
	private String title;
	
	@Column(length = 10000)
	@Size(min=0, max=10000)
	@ApiModelProperty("Summary describing the book")
	private String description;
	
	@Column(name = "unit_cost")
	@Min(1)
	@ApiModelProperty("Cost of the book")
	private Float unitCost;

	@Column(length = 50)
	@NotNull
	@Size(min=1, max=50)
	@ApiModelProperty("ISBN number")
	private String isbn; 
	
	@Column(name = "publication_date")
	@Temporal(TemporalType.DATE)
	@Past
	@ApiModelProperty("Book's date of publication")
	private Date publicationDate;
	
	@Column(name = "nb_of_pages")
	@ApiModelProperty("Number of pages in book")
	private Integer nbOfPages;
	
	@Column(name = "image_url")
	@ApiModelProperty("URL of the image cover")
	private String imageUrl;
	
	@ApiModelProperty("Language in which the book has been written")
	private Language language;
	
	public Book() {
		
	}

	public Book( String title, String description, Float unitCost, String isbn, Date publicationDate,
			Integer nbOfPages, String imageUrl, Language language) {
		this.title = title;
		this.description = description;
		this.unitCost = unitCost;
		this.isbn = isbn;
		this.publicationDate = publicationDate;
		this.nbOfPages = nbOfPages;
		this.imageUrl = imageUrl;
		this.language = language;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Float unitCost) {
		this.unitCost = unitCost;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Integer getNbOfPages() {
		return nbOfPages;
	}

	public void setNbOfPages(Integer nbOfPages) {
		this.nbOfPages = nbOfPages;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "Book {id=" + id + ", title=" + title + ", description=" + description + ", unitCost=" + unitCost
				+ ", isbn=" + isbn + ", publicationDate=" + publicationDate + ", nbOfPages=" + nbOfPages + ", imageUrl="
				+ imageUrl + ", language=" + language + "}";
	}
	
	
	
}
