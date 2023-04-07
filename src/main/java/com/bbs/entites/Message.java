package com.bbs.entites;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private Integer mid;
	
	@Column
	private String title;
	
	@Column
	@Nationalized
	private String message;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="did")
	private Details author;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="mfid")
	private MessageForum forum;
	
	public Message() {}
	
	public Message(String title, String message, Details author, MessageForum forum) {
		this.title = title;
		this.message = message;
		this.author = author;
		this.forum = forum;
	}

	public Integer getMid() {
		return mid;
	}
	public void setId(Integer mid) {
		this.mid = mid;
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

	public MessageForum getForum() {
		return forum;
	}

	public void setForum(MessageForum forum) {
		this.forum = forum;
	}

	@Override
	public String toString() {
		return "Message [id=" + mid + ", title=" + title + ", message=" + message + "]";
	}

}
