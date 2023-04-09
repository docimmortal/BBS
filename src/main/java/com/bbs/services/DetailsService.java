package com.bbs.services;

import java.util.List;
import java.util.Optional;

import com.bbs.entites.Details;

public interface DetailsService {

	public Optional<Details> findOptionalByUsername(String username);
	public Details save(Details details);
	public List<Details> findAll();
}
