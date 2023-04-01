package com.bbs.entites;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="message_forums")
public class MessageForum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mfid")
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String description;

	@JoinTable(
			name="message_forum_messages",
			joinColumns = @JoinColumn(
					name="mfpk",
					referencedColumnName = "mfid"),
			inverseJoinColumns = @JoinColumn(
	                name = "mpk",
	                referencedColumnName = "mid"))
	@OneToMany
	private Set<Message> messages;
	
	public MessageForum() {}
	
	public MessageForum(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}
	
}
