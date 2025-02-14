package com.bbs.entites;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	
	@Column
	private String title;
	
	@Column
	@Nationalized
	private String message;
	
	@Column
	private LocalDateTime timestamp;
	
	@ManyToOne
	private UserDetails userDetails;
	
	@ManyToOne
	private MessageForum messageForum;
	
	public Message() {}
	
	public Message(String title, String message, UserDetails userDetails, MessageForum messageForum) {
		this.title = title;
		this.message = message;
		this.userDetails = userDetails;
		this.messageForum = messageForum;
		this.timestamp = LocalDateTime.now();
		this.userDetails=userDetails;
		this.messageForum=messageForum;
	}
	
	public Message(String title, String message, LocalDateTime timestamp, UserDetails userDetails, MessageForum messageForum) {
		this.title = title;
		this.message = message;
		this.userDetails = userDetails;
		this.messageForum = messageForum;
		this.timestamp = timestamp;
		this.userDetails=userDetails;
		this.messageForum=messageForum;
	}

	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
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
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public MessageForum getMessageForum() {
		return messageForum;
	}

	public void setMessageForum(MessageForum messageForum) {
		this.messageForum = messageForum;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", title=" + title + ", message=" + message + "]";
	}

}
