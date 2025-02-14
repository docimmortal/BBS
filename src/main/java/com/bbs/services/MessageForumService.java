package com.bbs.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.bbs.entites.MessageForum;

public interface MessageForumService {

	public MessageForum save(MessageForum forum);
	public List<MessageForum> findAll();
	public Optional<MessageForum> findById(BigInteger id);
}
