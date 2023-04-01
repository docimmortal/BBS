package com.bbs.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbs.entites.Details;
import com.bbs.repos.DetailsRepository;

@Service
public class DetailsServiceImpl implements DetailsService {

	@Autowired
	private DetailsRepository repo;
	

	@Override
	public Optional<Details> findOptionalByUsername(String username) {
		return repo.findOptionalByUsername(username);
	}

}
