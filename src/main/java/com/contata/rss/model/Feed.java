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
@Table(name = "feed")
public class Feed {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int feedId;
	
	@Column(nullable=false)
	private int urlId;
	
	@ManyToOne
	@JoinColumn(name = "urlId",insertable = false, updatable = false)
	private Url urlObj;
	
	
	private String feeds;
	
	public int getImportId() {
		return feedId;
	}
	public void setImportId(int feedId) {
		this.feedId = feedId;
	}
	public Url getUrlObj() {
		return urlObj;
	}
	public void setUrlObj(Url urlObj) {
		this.urlObj = urlObj;
	}
	public int getUrlId() {
		return urlId;
	}
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	public String getImports() {
		return feeds;
	}
	public void setImports(String feeds) {
		this.feeds = feeds;
	}

}
