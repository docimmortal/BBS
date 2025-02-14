package com.bbs.services;

import java.util.List;
import java.util.Optional;

import com.bbs.entites.UserDetails;

public interface DetailsService {

	public Optional<UserDetails> findOptionalByUsername(String username);
	public UserDetails save(UserDetails details);
	public List<UserDetails> findAll();
}
