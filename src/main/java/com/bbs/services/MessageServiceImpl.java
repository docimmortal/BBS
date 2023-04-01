package com.bbs.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbs.entites.Message;
import com.bbs.repos.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository repo;
	
	@Override
	public Optional<Message> findById(Integer id) {
		return repo.findById(id);
	}

	@Override
	public Message save(Message message) {
		return repo.save(message);
	}

}
