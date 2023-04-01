package com.bbs.services;

import java.util.List;

import com.bbs.entites.MessageForum;

public interface MessageForumService {

	public MessageForum save(MessageForum forum);
	public List<MessageForum> findAll();
}
