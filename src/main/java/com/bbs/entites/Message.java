package com.bbs.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mid")
	private Integer id;
	
	@Column
	private String title;
	
	@Column
	private String message;
	
	@ManyToOne
    @JoinColumn(name="did", nullable=false)
	private Details author;
	
	@ManyToOne
    @JoinColumn(name="mfid", nullable=false)
	private MessageForum forum;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Details getAuthor() {
		return author;
	}
	public void setAuthor(Details author) {
		this.author = author;
	}
}