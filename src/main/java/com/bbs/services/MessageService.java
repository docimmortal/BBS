package com.bbs.services;

import java.math.BigInteger;
import java.util.Optional;

import com.bbs.entites.Message;

public interface MessageService {

	public Optional<Message> findById(BigInteger id);
	public Message save(Message message);
}
