package com.bbs.entites;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private Integer mfid;
	
	@Column
	private String name;
	
	@Column
	private String description;

	@JoinTable(
			name="message_forum_messages",
			joinColumns = {@JoinColumn(
					name="mfpk",
					referencedColumnName = "mfid")},
			inverseJoinColumns = {@JoinColumn(
	                name = "mpk",
	                referencedColumnName = "mid")})
	@OneToMany(fetch = FetchType.EAGER)
	private List<Message> messages;
	
	public MessageForum() {}
	
	public MessageForum(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Integer getMfid() {
		return mfid;
	}

	public void setMfid(Integer mfid) {
		this.mfid = mfid;
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

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "MessageForum [id=" + mfid + ", name=" + name + ", description=" + description + "]";
	}
	
}
