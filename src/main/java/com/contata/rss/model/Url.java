package com.contata.rss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "url")
public class Url {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int urlId;
	
	@Column(nullable = false,unique=true)
	private String url;
	
	@Column(nullable=false)
	private String  sourceParser;


	public int getUrlId() {
		return urlId;
	}
	
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSourceParser() {
		return sourceParser;
	}

	public void setSourceParser(String sourceParser) {
		this.sourceParser = sourceParser;
	}
	

}
