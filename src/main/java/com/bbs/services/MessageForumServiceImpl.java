package com.bbs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbs.entites.MessageForum;
import com.bbs.repos.MessageForumRepository;

@Service
public class MessageForumServiceImpl implements MessageForumService {

	@Autowired
	private MessageForumRepository repo;
	
	@Override
	public MessageForum save(MessageForum forum) {
		return repo.save(forum);
	}

	@Override
	public List<MessageForum> findAll() {
		return repo.findAll();
	}

}
