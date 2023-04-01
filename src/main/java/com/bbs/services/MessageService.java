package com.bbs.services;

import java.util.Optional;

import com.bbs.entites.Message;

public interface MessageService {

	public Optional<Message> findById(Integer id);
	public Message save(Message message);
}
