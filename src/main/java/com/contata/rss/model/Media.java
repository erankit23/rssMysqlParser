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
@Table(name = "media")
public class Media {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int mediaId;
	
	@Column(nullable=false)
	private int urlId;
	
	@ManyToOne
	@JoinColumn(name = "urlId",insertable = false, updatable = false)
	private Url urlObj;
	
	
	private String mediaType;
	
	@Column(length = 1000)
	private String mediaURL;
	
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaURL() {
		return mediaURL;
	}
	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
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
	

}
