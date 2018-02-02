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
@Table(name = "link")
public class Link {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int linkId;
	
	@Column(nullable=false)
	private int urlId;
	
	private String linkUrl;
	
	
	public int getLinkId() {
		return linkId;
	}


	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}


	public int getUrlId() {
		return urlId;
	}


	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}


	public String getLinkUrl() {
		return linkUrl;
	}


	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}


	public Url getUrlObj() {
		return urlObj;
	}


	public void setUrlObj(Url urlObj) {
		this.urlObj = urlObj;
	}


	@ManyToOne
	@JoinColumn(name = "urlId",insertable = false, updatable = false)
	private Url urlObj;
	
	
	
	
	
	

}
