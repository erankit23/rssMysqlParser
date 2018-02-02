package com.contata.rss.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "import")
public class Import {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int importId;
	
	@Column(nullable=false)
	private int urlId;
	
	@ManyToOne
	@JoinColumn(name = "urlId",insertable = false, updatable = false)
	private Url urlObj;
	
	
	public int getImportId() {
		return importId;
	}

	public void setImportId(int importId) {
		this.importId = importId;
	}

	public int getUrlId() {
		return urlId;
	}

	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}

	public Url getUrlObj() {
		return urlObj;
	}

	public void setUrlObj(Url urlObj) {
		this.urlObj = urlObj;
	}

	public String getImportLink() {
		return importLink;
	}

	public void setImportLink(String importLink) {
		this.importLink = importLink;
	}

	public String getImportCategory() {
		return importCategory;
	}

	public void setImportCategory(String importCategory) {
		this.importCategory = importCategory;
	}

	private String importLink;
	
	private String importCategory;
	

}
